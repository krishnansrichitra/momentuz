package com.momentus.foundation.entity.model;

import com.momentus.foundation.profile.model.ProfileGroup;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@jakarta.persistence.Entity
@Table(name = "entity")
public class Entity {

    @Id
    String entityName;

    @Column(name = "full_package", nullable = true)
    String fullPackage;


    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active =true ;


    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted =false ;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "profile_group_code" ,referencedColumnName = "profileGroupCode")
    ProfileGroup profileGroup;


    @Column(name = "created_by", nullable = true)
    private String createdBy;

    @Column(name = "created_time", nullable = true)
    private LocalDateTime createdTime;


    @Column(name = "last_updated_by", nullable = true)
    private String lastUpdatedBy;

    @Column(name = "last_updated_time", nullable = true)
    private LocalDateTime lastUpdatedTime;

    @Column(nullable = true)
    Boolean supportImport= true;


    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getFullPackage() {
        return fullPackage;
    }

    public void setFullPackage(String fullPackage) {
        this.fullPackage = fullPackage;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

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

    public ProfileGroup getProfileGroup() {
        return profileGroup;
    }

    public void setProfileGroup(ProfileGroup profileGroup) {
        this.profileGroup = profileGroup;
    }

    public Boolean getSupportImport() {
        return supportImport;
    }

    public void setSupportImport(Boolean supportImport) {
        this.supportImport = supportImport;
    }
}
