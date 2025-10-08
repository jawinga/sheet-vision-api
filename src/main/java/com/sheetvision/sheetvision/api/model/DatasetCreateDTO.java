package com.sheetvision.sheetvision.api.model;
import java.util.List;

public record DatasetCreateDTO(
        String name,
        List<String> columns,
        Integer rowCount
) {
}
