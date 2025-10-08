package com.sheetvision.sheetvision.api.mapper;

import com.sheetvision.sheetvision.api.model.Dataset;
import com.sheetvision.sheetvision.api.model.DatasetCreateDTO;
import com.sheetvision.sheetvision.api.model.DatasetDTO;

import java.time.LocalDateTime;


public class DatasetMapper {

    public DatasetMapper() {
    }

    public static Dataset toEntity(DatasetCreateDTO dto) {

        Dataset dataset = new Dataset();
        dataset.setFilename(dto.name());
        dataset.setColumns(dto.columns());
        dataset.setRowCount(dto.rowCount());
        dataset.setUploadedAt(LocalDateTime.now());

        return dataset;

    }

    public static DatasetDTO toDTO(Dataset dataset){

        return new DatasetDTO(
                dataset.getId(),
                dataset.getFilename(),
                dataset.getColumns(),
                dataset.getRowCount(),
                dataset.getUploadedAt()
        );
    }

}
