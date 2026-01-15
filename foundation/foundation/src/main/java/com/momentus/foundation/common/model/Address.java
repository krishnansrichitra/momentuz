package com.momentus.foundation.common.model;

import jakarta.persistence.*;

@Embeddable
public class Address {

  @Column(nullable = true)
  public String address1;

  public String address2;

  @Column(nullable = true)
  public String city;

  @Column(nullable = true)
  public String state;

  @Column(nullable = true)
  public String country;

  @Column(nullable = true, length = 10)
  public String zipcode;

  @Column(length = 15, nullable = true)
  public String phoneNumber;

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

  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
