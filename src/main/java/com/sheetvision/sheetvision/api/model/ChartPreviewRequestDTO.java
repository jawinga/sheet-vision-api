package com.sheetvision.sheetvision.api.model;
import java.util.List;
import java.util.Map;

public record ChartPreviewRequestDTO(

        List<Map<String, Object>> rows,
        String type,
        String xField,
        List<String> yFields,
        String aggregation

){}
