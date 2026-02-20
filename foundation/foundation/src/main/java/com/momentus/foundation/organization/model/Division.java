package com.momentus.foundation.organization.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.common.model.Address;
import jakarta.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "divisions")
public class Division extends OrgBasedEntity {

  @Column(nullable = false)
  @EntityProperties(isUnique = true)
  String divisionCode;

  @Column
  @EntityProperties(isBK = true)
  String title;

  @Embedded Address address;

  @Column String primaryContactName;

  @Column String primaryContactPhone;

  @Column String secondaryContactName;

  @Column String secondaryContactPhone;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_division_id") // nullable = true if root divisions exist
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Division parentDivision;

  public String getDivisionCode() {
    return divisionCode;
  }

  public void setDivisionCode(String divisionCode) {
    this.divisionCode = divisionCode;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public String getPrimaryContactName() {
    return primaryContactName;
  }

  public void setPrimaryContactName(String primaryContactName) {
    this.primaryContactName = primaryContactName;
  }

  public String getPrimaryContactPhone() {
    return primaryContactPhone;
  }

  public void setPrimaryContactPhone(String primaryContactPhone) {
    this.primaryContactPhone = primaryContactPhone;
  }

  public String getSecondaryContactName() {
    return secondaryContactName;
  }

  public void setSecondaryContactName(String secondaryContactName) {
    this.secondaryContactName = secondaryContactName;
  }

  public String getSecondaryContactPhone() {
    return secondaryContactPhone;
  }

  public void setSecondaryContactPhone(String secondaryContactPhone) {
    this.secondaryContactPhone = secondaryContactPhone;
  }

  public Division getParentDivision() {
    return parentDivision;
  }

  public void setParentDivision(Division parentDivision) {
    this.parentDivision = parentDivision;
  }

  @Override
  public Object getPK() {
    return id;
  }

  @Override
  public void setBK(Object object) {
    setTitle(String.valueOf(object));
  }

  @Override
  public Object getBKValue() {
    return title;
  }

  @Override
  public Map<String, Object> getBK() {
    Map<String, Object> result = new HashMap<>();
    result.put("title", title);
    return result;
  }
}
