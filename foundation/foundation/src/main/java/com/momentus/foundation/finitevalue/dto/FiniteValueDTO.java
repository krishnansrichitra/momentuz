package com.momentus.foundation.finitevalue.dto;

import com.momentus.foundation.finitevalue.model.FiniteValue;

public class FiniteValueDTO {

  String fvCode;

  String fvValue;

  public String getFvCode() {
    return fvCode;
  }

  public void setFvCode(String fvCode) {
    this.fvCode = fvCode;
  }

  public String getFvValue() {
    return fvValue;
  }

  public void setFvValue(String fvValue) {
    this.fvValue = fvValue;
  }

  public static FiniteValue makeFiniteValue(FiniteValueDTO finiteValueDTO) {
    return new FiniteValue(finiteValueDTO.getFvCode(), finiteValueDTO.getFvValue());
  }

  public FiniteValueDTO() {}

  public FiniteValueDTO(String fvCode, String fvValue) {
    this.fvCode = fvCode;
    this.fvValue = fvValue;
  }
}
