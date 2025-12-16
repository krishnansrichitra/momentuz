package com.momentus.foundation.common.model;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract  class BaseEntity {

    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted =false ;

    @Column(name = "created_by", nullable = true)
    private String createdBy;

    @Column(name = "created_time", nullable = true)
    private LocalDateTime createdTime;


    @Column(name = "last_updated_by", nullable = true)
    private String lastUpdatedBy;

    @Column(name = "last_updated_time", nullable = true)
    private LocalDateTime lastUpdatedTime;

    @Version
    private Long version;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public LocalDateTime getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(LocalDateTime lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Object getPK()
    {
        return null;
    }
}
