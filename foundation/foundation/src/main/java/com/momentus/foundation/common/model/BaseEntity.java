package com.momentus.foundation.common.model;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

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


}
