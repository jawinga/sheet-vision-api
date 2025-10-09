package com.sheetvision.sheetvision.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;

public record ChartPreviewRequestDTO(

        @NotBlank String type,                 // "bar" | "line" | "pie" | "doughnut"
        @NotBlank String xField,               // column for X
        @NotBlank String aggregation,          // "sum" | "avg" | "count" | "weighted_avg"

        @NotEmpty List<String> yFields,

        @NotNull @NotEmpty List<Map<String,Object>> rows,  // parsed rows (from FE)

        // ---- Optional enhancements ----
        String weightField,                    // for weighted_avg (and you can still send it for others)
        String sortLabels,                     // "none" | "alpha" | "numeric" | "date" (ISO yyyy-MM-dd or yyyy-MM)
        Boolean sortAsc,                       // default true
        Integer topN,                          // e.g., 8 (applies well to pie/bar)
        String othersLabel                     // e.g., "Others"
) {}