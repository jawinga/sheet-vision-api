package com.sheetvision.sheetvision.api.service;

import com.sheetvision.sheetvision.api.mapper.DatasetMapper;
import com.sheetvision.sheetvision.api.model.Dataset;
import com.sheetvision.sheetvision.api.model.DatasetCreateDTO;
import com.sheetvision.sheetvision.api.model.DatasetDTO;
import com.sheetvision.sheetvision.api.repository.DatasetRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
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

    public List<DatasetDTO> getAllDatasets(){

        return datasetRepository.findAll()
                .stream()
                .map(DatasetMapper::toDTO)
                .toList();
    }

    public DatasetDTO getDatasetById(Long id){
        Dataset dataset = datasetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dataset not found with id " + id));
        return DatasetMapper.toDTO(dataset);

    }

    public DatasetDTO updateDataset(Long id, DatasetCreateDTO dto){

        Dataset existing = datasetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dataset not found with id " + id));

        existing.setFilename(dto.name());
        existing.setRowCount(dto.rowCount());
        existing.setColumns(dto.columns());

        Dataset saved = datasetRepository.save(existing);
        return DatasetMapper.toDTO(saved);

    }

    public void deleteDataset(Long id){
        if (!datasetRepository.existsById(id)){
            throw new RuntimeException("Dataset not found with id " + id);
        }
        datasetRepository.deleteById(id);
    }


}
