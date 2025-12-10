package com.momentus.foundation.profile.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "profile")
public class Profile {

    @Id
    Long id;

    @Column(unique = true)
    String profileCode;

    @Column
    String parentProfileCode ;

    @Column
    String profileDescription;

    @Column(unique = true)
    String fullProfileCode;

    @Column
    Long parentProfileId;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(columnDefinition = "profile_group_code" , referencedColumnName = "profileGroupCode")
    ProfileGroup profileGroup;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active =true ;


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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProfileCode() {
        return profileCode;
    }

    public void setProfileCode(String profileCode) {
        this.profileCode = profileCode;
    }

    public String getParentProfileCode() {
        return parentProfileCode;
    }

    public void setParentProfileCode(String parentProfileCode) {
        this.parentProfileCode = parentProfileCode;
    }

    public String getFullProfileCode() {
        return fullProfileCode;
    }

    public void setFullProfileCode(String fullProfileCode) {
        this.fullProfileCode = fullProfileCode;
    }

    public Long getParentProfileId() {
        return parentProfileId;
    }

    public void setParentProfileId(Long parentProfileId) {
        this.parentProfileId = parentProfileId;
    }

    public ProfileGroup getProfileGroup() {
        return profileGroup;
    }

    public void setProfileGroup(ProfileGroup profileGroup) {
        this.profileGroup = profileGroup;
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
}
