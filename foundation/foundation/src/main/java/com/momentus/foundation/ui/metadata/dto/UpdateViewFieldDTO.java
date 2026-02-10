package com.momentus.foundation.ui.metadata.dto;

public class UpdateViewFieldDTO {

  String id;

  String fieldKey;

  String fieldLabel;

  String control;

  String param;

  String accessor;

  String visibility;

  String dType;

  String parent;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFieldKey() {
    return fieldKey;
  }

  public void setFieldKey(String fieldKey) {
    this.fieldKey = fieldKey;
  }

  public String getFieldLabel() {
    return fieldLabel;
  }

  public void setFieldLabel(String fieldLabel) {
    this.fieldLabel = fieldLabel;
  }

  public String getControl() {
    return control;
  }

  public void setControl(String control) {
    this.control = control;
  }

  public String getParam() {
    return param;
  }

  public void setParam(String param) {
    this.param = param;
  }

  public String getAccessor() {
    return accessor;
  }

  public void setAccessor(String accessor) {
    this.accessor = accessor;
  }

  public String getVisibility() {
    return visibility;
  }

  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }

  public UpdateViewFieldDTO() {}

  public UpdateViewFieldDTO(
      String id,
      String fieldKey,
      String fieldLabel,
      String control,
      String param,
      String accessor,
      String visibility,
      String dType,
      String parent) {
    this.id = id;
    this.fieldKey = fieldKey;
    this.fieldLabel = fieldLabel;
    this.control = control;
    this.param = param;
    this.accessor = accessor;
    this.visibility = visibility;
    this.dType = dType;
    this.parent = parent;
  }

  public String getdType() {
    return dType;
  }

  public void setdType(String dType) {
    this.dType = dType;
  }

  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }
}
