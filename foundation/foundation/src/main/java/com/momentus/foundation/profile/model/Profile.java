package com.momentus.foundation.profile.model;

import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.common.model.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "profile")
public class Profile extends BaseEntity {

    @Id
    @EntityProperties(isPK = true)
    Long id;

    @Column(unique = true)
    @EntityProperties(isBK = true)
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
    @JoinColumn(name = "profile_group_code" , referencedColumnName = "profileGroupCode")
    ProfileGroup profileGroup;

    @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean active =true ;




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

    @Override
    public Object getPK() {
        return id;
    }
}
