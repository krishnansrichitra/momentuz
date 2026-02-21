package com.momentus.foundation.accessgroup.dto;

import com.momentus.foundation.organization.dto.DivisionDTO;
import java.util.List;

public class UserDTO {

  String userId;

  String firstName;

  String lastName;

  String phone;

  String email;

  DivisionDTO division;

  List<UserRoleDTO> userRoles;

  Long version;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public DivisionDTO getDivision() {
    return division;
  }

  public void setDivision(DivisionDTO division) {
    this.division = division;
  }

  public List<UserRoleDTO> getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(List<UserRoleDTO> userRoles) {
    this.userRoles = userRoles;
  }

  public UserDTO() {}

  public UserDTO(
      String userId,
      String firstName,
      String lastName,
      String phone,
      String email,
      DivisionDTO division,
      List<UserRoleDTO> userRoles,
      Long version) {
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.email = email;
    this.division = division;
    this.userRoles = userRoles;
    this.version = version;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
