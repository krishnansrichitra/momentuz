package com.momentus.foundation.orgsignup.model;

import com.momentus.foundation.profile.model.Profile;
import jakarta.persistence.*;

@Entity
@Table(name = "sector_profile")
public class SectorProfileData {

  @Id String id;

  @Column String sector;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "profile_code", referencedColumnName = "profileCode")
  Profile profile;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSector() {
    return sector;
  }

  public void setSector(String sector) {
    this.sector = sector;
  }

  public Profile getProfile() {
    return profile;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }
}
