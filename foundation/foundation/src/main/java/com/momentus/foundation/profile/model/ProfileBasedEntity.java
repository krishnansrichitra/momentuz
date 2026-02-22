package com.momentus.foundation.profile.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.foundation.common.model.BaseEntity;
import jakarta.persistence.*;

@MappedSuperclass
public class ProfileBasedEntity extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "profile_code", referencedColumnName = "profileCode")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  Profile profile;

  public Profile getProfile() {
    return profile;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  @Column Integer profileLevel;

  public Integer getProfileLevel() {
    return profileLevel;
  }

  public void setProfileLevel(Integer profileLevel) {
    this.profileLevel = profileLevel;
  }
}
