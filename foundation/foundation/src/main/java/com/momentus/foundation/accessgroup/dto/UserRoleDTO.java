package com.momentus.foundation.accessgroup.dto;

public class UserRoleDTO {

  String userId;

  RoleDTO role;

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

  public UserRoleDTO(String userId, RoleDTO role) {
    this.userId = userId;
    this.role = role;
  }
}
