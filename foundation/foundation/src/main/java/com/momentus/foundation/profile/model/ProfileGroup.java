package com.momentus.foundation.profile.model;

import com.momentus.foundation.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "profile_group")
public class ProfileGroup extends BaseEntity {

  @Id String profileGroupCode;

  @Column(name = "profile_group_description", nullable = false)
  String profileGroupDescription;

  @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
  private boolean active = true;

  public String getProfileGroupCode() {
    return profileGroupCode;
  }

  public void setProfileGroupCode(String profileGroupCode) {
    this.profileGroupCode = profileGroupCode;
  }

  public String getProfileGroupDescription() {
    return profileGroupDescription;
  }

  public void setProfileGroupDescription(String profileGroupDescription) {
    this.profileGroupDescription = profileGroupDescription;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public Object getPK() {
    return profileGroupCode;
  }
}
