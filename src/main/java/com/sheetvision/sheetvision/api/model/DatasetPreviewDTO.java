package com.sheetvision.sheetvision.api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public record DatasetPreviewDTO(

        String name,
        List<String> columns,
        Integer rowCount,
        LocalDateTime createdAt,
        String contentType,

        List<List<Object>> sampleRows

) {
}
