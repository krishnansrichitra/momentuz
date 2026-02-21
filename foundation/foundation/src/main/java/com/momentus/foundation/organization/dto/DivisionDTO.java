package com.momentus.foundation.organization.dto;

public class DivisionDTO {

  Long id;

  String title;

  String divisionCode;

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

  public String getDivisionCode() {
    return divisionCode;
  }

  public void setDivisionCode(String divisionCode) {
    this.divisionCode = divisionCode;
  }

  public DivisionDTO() {}

  public DivisionDTO(Long id, String title, String divisionCode) {
    this.id = id;
    this.title = title;
    this.divisionCode = divisionCode;
  }
}
