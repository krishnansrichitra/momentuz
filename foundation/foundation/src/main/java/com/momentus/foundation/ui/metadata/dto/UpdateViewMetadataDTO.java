package com.momentus.foundation.ui.metadata.dto;

import java.util.List;

public class UpdateViewMetadataDTO {

  String entity;

  String title;

  String jsFile;

  List<UpdateViewButtonDTO> updateViewButtons;

  List<UpdateViewFieldDTO> updateViewFields;

  public String getEntity() {
    return entity;
  }

  public void setEntity(String entity) {
    this.entity = entity;
  }

  public List<UpdateViewButtonDTO> getUpdateViewButtons() {
    return updateViewButtons;
  }

  public void setUpdateViewButtons(List<UpdateViewButtonDTO> updateViewButtons) {
    this.updateViewButtons = updateViewButtons;
  }

  public List<UpdateViewFieldDTO> getUpdateViewFields() {
    return updateViewFields;
  }

  public void setUpdateViewFields(List<UpdateViewFieldDTO> updateViewFields) {
    this.updateViewFields = updateViewFields;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getJsFile() {
    return jsFile;
  }

  public void setJsFile(String jsFile) {
    this.jsFile = jsFile;
  }
}
