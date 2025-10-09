package com.sheetvision.sheetvision.api.controller;

import com.sheetvision.sheetvision.api.service.ChartPreviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/charts")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "http://localhost:4200")
public class ChartPreviewController {

    public ChartPreviewService chartPreviewService;


}
