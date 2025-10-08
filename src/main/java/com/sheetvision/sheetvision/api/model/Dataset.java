package com.sheetvision.sheetvision.api.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table (name = "dataset")
public class Dataset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename; //DTO

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    @ElementCollection
    @CollectionTable(name = "dataset_columns", joinColumns = @JoinColumn(name = "dataset_id"))
    @Column(name = "column_name")
    private List<String>columns = new ArrayList<>();

    private String contentType; //DTO

    private long size; //DTO

    private LocalDateTime uploadedAt = LocalDateTime.now(); //DTO

    private String storagePath;

    private String uploadedBy;

    private String status;

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    private Integer rowCount; //DTO


    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }


}
