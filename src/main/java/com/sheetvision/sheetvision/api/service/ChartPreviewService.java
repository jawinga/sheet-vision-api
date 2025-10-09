package com.sheetvision.sheetvision.api.service;

import com.sheetvision.sheetvision.api.model.ChartPreviewRequestDTO;
import com.sheetvision.sheetvision.api.model.ChartPreviewResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChartPreviewService {

    public ChartPreviewResponse build(ChartPreviewRequestDTO req) {
        String agg = safeLower(req.aggregation());
        boolean countOnly = "count".equals(agg);

        List<String> yFields = (req.yFields() == null) ? List.of() : req.yFields();
        String weightField = req.weightField();
        boolean sortAsc = req.sortAsc() == null || Boolean.TRUE.equals(req.sortAsc());
        String sortMode = req.sortLabels() == null ? "none" : req.sortLabels().toLowerCase(Locale.ROOT);
        Integer topN = req.topN();
        String othersLabel = (req.othersLabel() == null || req.othersLabel().isBlank()) ? "Others" : req.othersLabel();

        // label -> (series -> Stats)
        LinkedHashMap<String, Map<String, Stats>> buckets = new LinkedHashMap<>();

        for (Map<String, Object> row : req.rows()) {
            String label = String.valueOf(row.get(req.xField()));
            Map<String, Stats> group = buckets.computeIfAbsent(label, k -> new HashMap<>());

            if (countOnly) {
                group.computeIfAbsent("_count", k -> new Stats()).add(1.0, 1.0);
            } else {
                for (String y : yFields) {
                    double v = toFiniteDouble(row.get(y));
                    double w = (weightField != null && !weightField.isBlank())
                            ? toFiniteDouble(row.get(weightField))
                            : 1.0;
                    group.computeIfAbsent(y, k -> new Stats()).add(v, w);
                }
            }
        }

        // sort labels (alpha | numeric | date | none)
        List<String> labels = new ArrayList<>(buckets.keySet());
        sortLabels(labels, sortMode, sortAsc);

        // top-N with Others (useful for pie/bar)
        if (topN != null && topN > 0 && labels.size() > topN) {
            // rank labels by total across all y series (or _count)
            Map<String, Double> totals = new HashMap<>();
            for (String label : labels) {
                Map<String, Stats> m = buckets.get(label);
                double t = 0.0;
                if (countOnly) {
                    t = (m.get("_count") != null) ? m.get("_count").get("sum") : 0.0;
                } else {
                    for (String y : yFields) {
                        Stats s = m.get(y);
                        if (s != null) t += s.get("sum");
                    }
                }
                totals.put(label, t);
            }
            // keep topN by descending total
            List<String> sortedByTotalDesc = labels.stream()
                    .sorted(Comparator.comparingDouble((String l) -> totals.getOrDefault(l, 0.0)).reversed())
                    .collect(Collectors.toList());

            List<String> keep = sortedByTotalDesc.subList(0, topN);
            List<String> drop = sortedByTotalDesc.subList(topN, sortedByTotalDesc.size());

            // merge dropped into "Others"
            Map<String, Stats> othersMap = new HashMap<>();
            for (String d : drop) {
                Map<String, Stats> m = buckets.get(d);
                if (m == null) continue;
                for (var e : m.entrySet()) {
                    Stats dest = othersMap.computeIfAbsent(e.getKey(), k -> new Stats());
                    dest.mergeFrom(e.getValue());
                }
            }
            // rebuild buckets and labels
            LinkedHashMap<String, Map<String, Stats>> newBuckets = new LinkedHashMap<>();
            for (String k : keep) newBuckets.put(k, buckets.get(k));
            newBuckets.put(othersLabel, othersMap);
            buckets = newBuckets;

            // respect original sort mode for final labels
            labels = new ArrayList<>(buckets.keySet());
            sortLabels(labels, sortMode, sortAsc);
        }

        // build series per aggregation
        Map<String, List<Number>> series = new LinkedHashMap<>();
        if (countOnly) {
            List<Number> data = new ArrayList<>();
            for (String label : labels) {
                Stats s = buckets.get(label).get("_count");
                data.add(s != null ? s.get("sum") : 0.0);
            }
            series.put("_count", data);
        } else {
            for (String y : yFields) {
                List<Number> data = new ArrayList<>();
                for (String label : labels) {
                    Stats s = buckets.get(label).get(y);
                    data.add(s != null ? s.get(agg) : 0.0);
                }
                series.put(y, data);
            }
        }

        return new ChartPreviewResponse(req.type(), labels, series);
    }

    //helpers

    private static String safeLower(String s) {
        return s == null ? "" : s.toLowerCase(Locale.ROOT);
    }

    private static double toFiniteDouble(Object o) {
        double v;
        if (o == null) return 0.0;
        if (o instanceof Number n) v = n.doubleValue();
        else {
            try { v = Double.parseDouble(o.toString()); }
            catch (Exception e) { return 0.0; }
        }
        return (Double.isFinite(v)) ? v : 0.0;
    }

    //sort layers by mode
    private static void sortLabels(List<String> labels, String mode, boolean asc) {
        Comparator<String> cmp = null;
        switch (mode) {
            case "alpha" -> cmp = Comparator.naturalOrder();
            case "numeric" -> cmp = Comparator.comparingDouble(ChartPreviewService::parseDoubleSafe);
            case "date" -> cmp = Comparator.comparing(ChartPreviewService::parseDateSafe);
            default -> { /* none */ }
        }
        if (cmp != null) {
            if (!asc) cmp = cmp.reversed();
            labels.sort(cmp);
        }
    }

    private static double parseDoubleSafe(String s) {
        try { return Double.parseDouble(s); } catch (Exception e) { return Double.NaN; }
    }

    private static LocalDate parseDateSafe(String s) {
        // expects ISO like 2024-01-15 or 2024-01
        try {
            if (s.matches("\\d{4}-\\d{2}$")) s = s + "-01";
            return LocalDate.parse(s);
        } catch (Exception e) {
            // fallback: push unparsable dates to the end by returning max date
            return LocalDate.of(9999, 12, 31);
        }
    }


    private static class Stats {
        double sum = 0.0;
        int count = 0;
        double weightedSum = 0.0;
        double weightSum = 0.0;

        void add(double val, double weight) {
            sum += val;
            count += 1;
            weightedSum += val * weight;
            weightSum += weight;
        }

        void mergeFrom(Stats other) {
            this.sum += other.sum;
            this.count += other.count;
            this.weightedSum += other.weightedSum;
            this.weightSum += other.weightSum;
        }

        double get(String agg) {
            return switch (agg) {
                case "sum" -> sum;
                case "avg", "mean" -> count > 0 ? sum / count : 0.0;
                case "weighted_avg" -> (weightSum > 0 ? weightedSum / weightSum : 0.0);
                case "count" -> count;
                default -> sum; // fallback
            };
        }
    }
}