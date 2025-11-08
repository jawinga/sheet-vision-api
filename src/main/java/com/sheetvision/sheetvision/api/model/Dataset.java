package com.sheetvision.sheetvision.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "dataset")
public class Dataset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @ElementCollection
    @CollectionTable(name = "dataset_columns", joinColumns = @JoinColumn(name = "dataset_id"))
    @Column(name = "column_name")
    private List<String> columns = new ArrayList<>();

    private String contentType;

    private long size;

    private LocalDateTime uploadedAt = LocalDateTime.now();

    private String storagePath;

    private String uploadedBy;

    private String status;

    private Integer rowCount;

    // Manual Getters
    public Long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public List<String> getColumns() {
        return columns;
    }

    public String getContentType() {
        return contentType;
    }

    public long getSize() {
        return size;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public String getStoragePath() {
        return storagePath;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public String getStatus() {
        return status;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    // Manual Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
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

    public void setStoragePath(String storagePath) {
        this.storagePath = storagePath;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }
}