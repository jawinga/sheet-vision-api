package com.sheetvision.sheetvision.api.model;

import java.time.LocalDateTime;
import java.util.List;


public record ChartConfigDTO(

        Long id,
        Long datasetId,
        String type, // "bar", "line", "pie", "doughnut"
        String xField, // field for x-axis
        List<String>yFields, // fields for y-axis
        String abbreviation, // "sum", "avg", "count" (nullable/optional)
        LocalDateTime createdAt

) {
}
