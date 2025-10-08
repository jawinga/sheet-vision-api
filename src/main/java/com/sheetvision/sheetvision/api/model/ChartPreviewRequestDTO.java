package com.sheetvision.sheetvision.api.model;
import java.util.List;

public record ChartPreviewRequestDTO(

        Long datasetId,          // tell backend which dataset to use
        String type,             // "bar" | "line" | "pie" | "doughnut"
        String xField,           // column name for X-axis
        List<String> yFields,    // column names for Y-values
        String aggregation       // "sum" | "avg" | "count") {

){}
