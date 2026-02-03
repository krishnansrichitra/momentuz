package com.momentus.foundation.orgsignup.model;

import com.momentus.foundation.profile.model.Profile;
import jakarta.persistence.*;

@Entity
@Table(name = "primary_user_role")
public class PrimaryUserRole {

  @Id String roleDescription;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "profile_code", referencedColumnName = "profileCode")
  Profile profile;

  @Column(columnDefinition = "BLOB")
  String accessCodes;

  public String getRoleDescription() {
    return roleDescription;
  }

  public void setRoleDescription(String roleDescription) {
    this.roleDescription = roleDescription;
  }

  public Profile getProfile() {
    return profile;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public String getAccessCodes() {
    return accessCodes;
  }

  public void setAccessCodes(String accessCodes) {
    this.accessCodes = accessCodes;
  }
}
