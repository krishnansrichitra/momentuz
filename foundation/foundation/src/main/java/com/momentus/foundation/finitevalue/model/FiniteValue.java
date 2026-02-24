package com.momentus.foundation.finitevalue.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.foundation.profile.model.Profile;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "finite_value")
public class FiniteValue {

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "profile_code", referencedColumnName = "profileCode")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  Profile profile;

  public Profile getProfile() {
    return profile;
  }

  public void setProfile(Profile profile) {
    this.profile = profile;
  }

  @Column BigDecimal seqNo;

  @Id
  @Column(name = "fv_code")
  String fvCode;

  @Column(name = "fv_value")
  String fvValue;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "group_code", referencedColumnName = "group_code")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  FiniteGroup finiteGroup;

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

  public FiniteGroup getFiniteGroup() {
    return finiteGroup;
  }

  public void setFiniteGroup(FiniteGroup finiteGroup) {
    this.finiteGroup = finiteGroup;
  }

  public FiniteValue(String fvCode) {
    this.fvCode = fvCode;
  }

  public FiniteValue() {}

  public BigDecimal getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(BigDecimal seqNo) {
    this.seqNo = seqNo;
  }
}
