package com.momentus.foundation.organization.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.foundation.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "sector")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Sector extends BaseEntity {

  @Id
  @Column(length = 20)
  String code;

  @Column String name;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public Object getPK() {
    return code;
  }
}
