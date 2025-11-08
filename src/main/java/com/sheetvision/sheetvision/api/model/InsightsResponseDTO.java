package com.sheetvision.sheetvision.api.model;
import java.util.List;


public record InsightsResponseDTO(
        Boolean success,
        String error,
        String trends,
        List<String> anomalies,
        String recommendations
) {}
