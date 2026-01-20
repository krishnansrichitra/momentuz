package com.momentus.foundation.organization.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.common.model.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "organization")
public class Organization extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @EntityProperties(isPK = true)
  Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.MERGE)
  @JoinColumn(name = "industry_code", referencedColumnName = "code", nullable = true)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  Industry industry;

  @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.MERGE)
  @JoinColumn(name = "sector_code", referencedColumnName = "code", nullable = true)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  Sector sector;

  @Column(nullable = false)
  @EntityProperties(isBK = true)
  private String orgCode;

  @Column(nullable = false)
  @EntityProperties(isMandatory = true)
  private String organizationName;

  @Column(nullable = false)
  private String address1;

  @Column private String address2;

  @Column private String zipCode;

  @Column private String city;

  @Column private String state;

  @Column private String country;

  @Column(nullable = false)
  @EntityProperties(isMandatory = true)
  private String email;

  @Column(nullable = false)
  @EntityProperties(isMandatory = true)
  private String phone;

  @Column private String primaryContact;

  @Column(columnDefinition = "BOOLEAN DEFAULT TRUE")
  private Boolean active;

  @Column LocalDate registrationDate;

  @Column(columnDefinition = "BLOB")
  String supplimentaryInfo;

  @Column Boolean trialPeriod;

  public String getOrgCode() {
    return orgCode;
  }

  public void setOrgCode(String orgCode) {
    this.orgCode = orgCode;
  }

  public String getOrganizationName() {
    return organizationName;
  }

  public void setOrganizationName(String organizationName) {
    this.organizationName = organizationName;
  }

  public String getAddress1() {
    return address1;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public String getAddress2() {
    return address2;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPrimaryContact() {
    return primaryContact;
  }

  public void setPrimaryContact(String primaryContact) {
    this.primaryContact = primaryContact;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Industry getIndustry() {
    return industry;
  }

  public void setIndustry(Industry industry) {
    this.industry = industry;
  }

  public Sector getSector() {
    return sector;
  }

  public void setSector(Sector sector) {
    this.sector = sector;
  }

  @Override
  public Object getPK() {
    return id;
  }

  public LocalDate getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(LocalDate registrationDate) {
    this.registrationDate = registrationDate;
  }

  public String getSupplimentaryInfo() {
    return supplimentaryInfo;
  }

  public void setSupplimentaryInfo(String supplimentaryInfo) {
    this.supplimentaryInfo = supplimentaryInfo;
  }

  public Boolean getTrialPeriod() {
    return trialPeriod;
  }

  public void setTrialPeriod(Boolean trialPeriod) {
    this.trialPeriod = trialPeriod;
  }
}
