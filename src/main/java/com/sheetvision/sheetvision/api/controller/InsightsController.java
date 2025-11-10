package com.sheetvision.sheetvision.api.controller;

import com.sheetvision.sheetvision.api.model.DatasetCreateDTO;
import com.sheetvision.sheetvision.api.model.DatasetDTO;
import com.sheetvision.sheetvision.api.model.InsightsRequestDTO;
import com.sheetvision.sheetvision.api.model.InsightsResponseDTO;
import com.sheetvision.sheetvision.api.service.AnthropicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/insights")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class InsightsController {

    private final AnthropicService anthropicService;


    @PostMapping
    public ResponseEntity<InsightsResponseDTO> getInsights(@Valid  @RequestBody InsightsRequestDTO request) {

        InsightsResponseDTO response = anthropicService.generateInsights(request);
        return ResponseEntity.ok(response);

        }

    }
