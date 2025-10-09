package com.sheetvision.sheetvision.api.controller;

import com.sheetvision.sheetvision.api.model.DatasetDTO;
import com.sheetvision.sheetvision.api.service.DatasetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/datasets")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:4200") //Angular
public class DatasetController {

    private final DatasetService datasetService;

    //get all datasets
    @GetMapping
    public ResponseEntity<List<DatasetDTO>> list(){
        return ResponseEntity.ok(datasetService.getAllDatasets());
    }

    //get dataset by id

    @GetMapping("/{id}")
    public ResponseEntity<DatasetDTO> getById(@PathVariable Long id){
        return ResponseEntity.ok(datasetService.getDatasetById(id));
    }

}
