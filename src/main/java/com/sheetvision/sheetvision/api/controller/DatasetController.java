package com.sheetvision.sheetvision.api.controller;

import com.sheetvision.sheetvision.api.model.DatasetCreateDTO;
import com.sheetvision.sheetvision.api.model.DatasetDTO;
import com.sheetvision.sheetvision.api.service.DatasetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    //create dataset
    @PostMapping
    public ResponseEntity<DatasetDTO> createDataset(@RequestBody DatasetCreateDTO dto){

        DatasetDTO created = datasetService.createDataset(dto);

        URI location = URI.create("/api/datasets/" + created.id());

        return ResponseEntity.created(location).body(created);
    }

    //update dataset
    @PutMapping("/{id}")
    public ResponseEntity<DatasetDTO> updateById(@PathVariable Long id, @RequestBody DatasetCreateDTO dto){
        return ResponseEntity.ok(datasetService.updateDataset(id, dto));
    }

    //delete dataset
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDataset(@PathVariable Long id){
        datasetService.deleteDataset(id);
    }

}
