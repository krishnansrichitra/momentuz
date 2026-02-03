package com.momentus.foundation.organization.model;

import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.profile.model.Profile;
import com.momentus.foundation.profile.model.ProfileGroup;
import jakarta.persistence.*;

@Entity
@Table(name = "org_profile")
public class OrgProfile extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EntityProperties(isPK = true)
  Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "org_id", referencedColumnName = "Id")
  Organization orgId;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "profile_code", referencedColumnName = "profileCode")
  Profile profile;

  @ManyToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "profile_group_code", referencedColumnName = "profileGroupCode")
  ProfileGroup profileGroup;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Profile getProfile() {
    return profile;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  public ProfileGroup getProfileGroup() {
    return profileGroup;
  }

  public void setProfileGroup(ProfileGroup profileGroup) {
    this.profileGroup = profileGroup;
  }

  public Organization getOrgId() {
    return orgId;
  }

  public void setOrgId(Organization orgId) {
    this.orgId = orgId;
  }
}
