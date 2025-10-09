package com.sheetvision.sheetvision.api.service;

import com.sheetvision.sheetvision.api.model.ChartPreviewRequestDTO;
import com.sheetvision.sheetvision.api.model.ChartPreviewResponse;
import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

@Service
public class ChartPreviewService {

    public ChartPreviewResponse build(ChartPreviewRequestDTO req) {
        LinkedHashMap<String, Map<String, Double>> buckets = new LinkedHashMap<>();

        for (Map<String,Object> row : req.rows()) {
            String key = String.valueOf(row.get(req.xField()));
            buckets.computeIfAbsent(key, k -> new HashMap<>());
            for (String y : req.yFields()) {
                double val = toDouble(row.get(y));
                buckets.get(key).merge(y, val, Double::sum);
            }
            if ("count".equalsIgnoreCase(req.aggregation())) {
                buckets.get(key).merge("_count", 1.0, Double::sum);
            }
        }

        if ("avg".equalsIgnoreCase(req.aggregation())) {
            Map<String,Integer> counts = new HashMap<>();
            for (Map<String,Object> row : req.rows()) {
                String key = String.valueOf(row.get(req.xField()));
                counts.merge(key, 1, Integer::sum);
            }
            for (var e : buckets.entrySet()) {
                int c = counts.getOrDefault(e.getKey(), 1);
                for (String y : req.yFields()) {
                    e.getValue().computeIfPresent(y, (k,v) -> v / c);
                }
            }
        }

        List<String> labels = new ArrayList<>(buckets.keySet());
        Map<String, List<Number>> series = new LinkedHashMap<>();
        for (String y : req.yFields()) {
            List<Number> data = new ArrayList<>();
            for (String label : labels) {
                data.add(buckets.get(label).getOrDefault(y, 0.0));
            }
            series.put(y, data);
        }
        return new ChartPreviewResponse(req.type(), labels, series);
    }

    private double toDouble(Object o) {
        if (o == null) return 0.0;
        if (o instanceof Number n) return n.doubleValue();
        try { return Double.parseDouble(o.toString()); }
        catch (Exception e) { return 0.0; }
    }
}
