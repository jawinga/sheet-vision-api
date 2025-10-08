package com.sheetvision.sheetvision.api.model;
import java.time.LocalDateTime;
import java.util.List;

public record DatasetDTO(
        Long id,
        String name,
        List<String> columns,
        Integer rowCount,
        LocalDateTime uploadedAt
) {}
