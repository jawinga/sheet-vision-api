package com.sheetvision.sheetvision.api.controller;

import com.sheetvision.sheetvision.api.mapper.DatasetMapper;
import com.sheetvision.sheetvision.api.model.Dataset;
import com.sheetvision.sheetvision.api.repository.DatasetRepository;
import com.sheetvision.sheetvision.api.service.DatasetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/dataset")
public class SheetVisionAPIController {

    private DatasetService datasetService;

    private DatasetMapper datasetMapper;

    private final DatasetRepository datasetRepository;

    public SheetVisionAPIController(DatasetRepository datasetRepository){

        this.datasetRepository = datasetRepository;

    }

    @GetMapping
    Iterable<Dataset> getDataset(){

        return datasetRepository.findAll();

    }

    @GetMapping("/{id}")
    Optional<Dataset> getDatasetById(@PathVariable Long id){
        return datasetRepository.findById(id);
    }

    @PostMapping
    Dataset postDataset(Dataset dataset){
        return datasetRepository.save(dataset);
    }

    @PutMapping
    ResponseEntity<Dataset> putDataset(@PathVariable Long id, @RequestBody Dataset dataset){

        return (datasetRepository.existsById(id))?
                new ResponseEntity<>(datasetRepository.save(dataset), HttpStatus.OK)
                :
                new ResponseEntity<>(datasetRepository.save(dataset), HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    void deleteDataset(@PathVariable Long id){
        datasetRepository.deleteById(id);
    }


}
