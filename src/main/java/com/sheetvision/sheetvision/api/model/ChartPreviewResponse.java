package com.sheetvision.sheetvision.api.model;
import java.util.List;
import java.util.Map;

public record ChartPreviewResponse(

        String type,                    // chart type (echoed back)
        List<String> labels,            // x-axis categories
        Map<String, List<Number>> series // e.g. {"sales": [100,200,300]}

) {

}
