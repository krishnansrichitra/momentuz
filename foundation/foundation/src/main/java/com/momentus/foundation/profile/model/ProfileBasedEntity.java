package com.momentus.foundation.profile.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.common.model.BaseEntity;
import jakarta.persistence.*;

@MappedSuperclass
public class ProfileBasedEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", referencedColumnName = "Id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Profile profile;


    @Column
    String profileCode;



    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getProfileCode() {
        return profileCode;
    }

    public void setProfileCode(String profileCode) {
        this.profileCode = profileCode;
    }

}
