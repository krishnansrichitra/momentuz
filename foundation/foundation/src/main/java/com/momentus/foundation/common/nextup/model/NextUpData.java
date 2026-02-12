package com.momentus.foundation.common.nextup.model;

import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "nextup_data")
public class NextUpData extends OrgBasedEntity {

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "config_id", referencedColumnName = "id")
  NextUpConfig config;

  @Column Long lastSeqValue;

  @Column String component1;

  @Column String component2;

  @Column String component3;

  @Column LocalDate lastDate;

  public NextUpConfig getConfig() {
    return config;
  }

  public void setConfig(NextUpConfig config) {
    this.config = config;
  }

  public Long getLastSeqValue() {
    return lastSeqValue;
  }

  public void setLastSeqValue(Long lastSeqValue) {
    this.lastSeqValue = lastSeqValue;
  }

  public String getComponent1() {
    return component1;
  }

  public void setComponent1(String component1) {
    this.component1 = component1;
  }

  public String getComponent2() {
    return component2;
  }

  public void setComponent2(String component2) {
    this.component2 = component2;
  }

  public String getComponent3() {
    return component3;
  }

  public void setComponent3(String component3) {
    this.component3 = component3;
  }

  public LocalDate getLastDate() {
    return lastDate;
  }

  public void setLastDate(LocalDate lastDate) {
    this.lastDate = lastDate;
  }
}
