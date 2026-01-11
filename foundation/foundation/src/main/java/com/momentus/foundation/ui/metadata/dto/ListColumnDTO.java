package com.momentus.foundation.ui.metadata.dto;

public class ListColumnDTO {

  String fieldkey;

  String fieldLabel;

  String accessor;

  public String getFieldkey() {
    return fieldkey;
  }

  public void setFieldkey(String fieldkey) {
    this.fieldkey = fieldkey;
  }

  public String getFieldLabel() {
    return fieldLabel;
  }

  public void setFieldLabel(String fieldLabel) {
    this.fieldLabel = fieldLabel;
  }

  public ListColumnDTO() {}

  public ListColumnDTO(String fieldkey, String fieldLabel, String accessor) {
    this.fieldkey = fieldkey;
    this.fieldLabel = fieldLabel;
    this.accessor = accessor;
  }

  public String getAccessor() {
    return accessor;
  }

  public void setAccessor(String accessor) {
    this.accessor = accessor;
  }
}
