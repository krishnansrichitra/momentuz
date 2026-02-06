package com.momentus.foundation.common.nextup.model;

import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "nextup_data")
public class NextUpData extends OrgBasedEntity {

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "config_id", referencedColumnName = "id")
  NextUpConfig config;

  @Column Long lastSeqValue;

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
}
