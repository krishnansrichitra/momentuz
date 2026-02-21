package com.momentus.foundation.accessgroup.dto;

public class RoleDTO {

  Long id;

  String title;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public RoleDTO() {}

  public RoleDTO(Long id, String title) {
    this.id = id;
    this.title = title;
  }
}
