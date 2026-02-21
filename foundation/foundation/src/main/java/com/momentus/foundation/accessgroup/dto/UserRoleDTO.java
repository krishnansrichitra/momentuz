package com.momentus.foundation.accessgroup.dto;

public class UserRoleDTO {

  Long id;

  String userId;

  RoleDTO role;

  Long version;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public RoleDTO getRole() {
    return role;
  }

  public void setRole(RoleDTO role) {
    this.role = role;
  }

  public UserRoleDTO() {}

  public UserRoleDTO(Long id, String userId, RoleDTO role, Long version) {
    this.id = id;
    this.userId = userId;
    this.role = role;
    this.version = version;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
