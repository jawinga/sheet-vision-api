package com.sheetvision.sheetvision.api.model;
import java.util.List;
import java.util.Map;

public record InsightsRequestDTO(

        String fileName,
        String sheetName,
        Integer rowCount,
        List<Map<String, Object>> rows,

        List<String> columns,
        List <Map<String, Object>> sampleRows) {


}
