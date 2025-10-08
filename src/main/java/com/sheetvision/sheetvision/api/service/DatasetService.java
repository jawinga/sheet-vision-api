package com.sheetvision.sheetvision.api.service;

import com.sheetvision.sheetvision.api.mapper.DatasetMapper;
import com.sheetvision.sheetvision.api.model.Dataset;
import com.sheetvision.sheetvision.api.model.DatasetCreateDTO;
import com.sheetvision.sheetvision.api.model.DatasetDTO;
import com.sheetvision.sheetvision.api.repository.DatasetRepository;


public class DatasetService {

    private final DatasetRepository datasetRepository;


    public DatasetService(DatasetRepository datasetRepository){
        this.datasetRepository = datasetRepository;
    }

    public DatasetDTO createDataset(DatasetCreateDTO dto) {
        Dataset dataset = DatasetMapper.toEntity(dto);
        Dataset saved = datasetRepository.save(dataset);
        return DatasetMapper.toDTO(saved);
    }


}
