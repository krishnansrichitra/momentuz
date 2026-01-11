package com.momentus.foundation.ui.metadata.dto;

import java.math.BigDecimal;

public class UpdateViewButtonDTO {

  String id;

  String buttonClass;

  String jsMethod;

  String innerText;

  String visibility;

  BigDecimal seqNo;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getButtonClass() {
    return buttonClass;
  }

  public void setButtonClass(String buttonClass) {
    this.buttonClass = buttonClass;
  }

  public String getJsMethod() {
    return jsMethod;
  }

  public void setJsMethod(String jsMethod) {
    this.jsMethod = jsMethod;
  }

  public String getInnerText() {
    return innerText;
  }

  public void setInnerText(String innerText) {
    this.innerText = innerText;
  }

  public String getVisibility() {
    return visibility;
  }

  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }

  public BigDecimal getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(BigDecimal seqNo) {
    this.seqNo = seqNo;
  }

  public UpdateViewButtonDTO() {}

  public UpdateViewButtonDTO(
      String id,
      String buttonClass,
      String jsMethod,
      String innerText,
      String visibility,
      BigDecimal seqNo) {
    this.id = id;
    this.buttonClass = buttonClass;
    this.jsMethod = jsMethod;
    this.innerText = innerText;
    this.visibility = visibility;
    this.seqNo = seqNo;
  }
}
