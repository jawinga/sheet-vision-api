package com.sheetvision.sheetvision.api.controller;

import com.sheetvision.sheetvision.api.model.ChartPreviewRequestDTO;
import com.sheetvision.sheetvision.api.model.ChartPreviewResponse;
import com.sheetvision.sheetvision.api.service.ChartPreviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/charts")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class ChartPreviewController {

    private final ChartPreviewService chartPreviewService;

    @PostMapping(path = "/preview",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChartPreviewResponse> preview(@Valid @RequestBody ChartPreviewRequestDTO req){
        return ResponseEntity.ok(chartPreviewService.build(req));
    }


}
