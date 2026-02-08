package com.momentus.foundation.common.nextup.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.profile.model.ProfileBasedEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "nextup_config")
public class NextUpConfig extends ProfileBasedEntity {

  @Id String id;

  @Column String entity;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "field_1", referencedColumnName = "fv_code")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  FiniteValue field1;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "field_2", referencedColumnName = "fv_code")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  FiniteValue field2;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "field_3", referencedColumnName = "fv_code")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  FiniteValue field3;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "field_4", referencedColumnName = "fv_code")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  FiniteValue field4;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "field_5", referencedColumnName = "fv_code")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  FiniteValue field5;

  @Column String prefix;

  @Column String dateFormat;

  @Column Boolean resetSeqPerDay;

  @Column Integer sequenceWidth;

  public String getEntity() {
    return entity;
  }

  public void setEntity(String entity) {
    this.entity = entity;
  }

  public FiniteValue getField1() {
    return field1;
  }

  public void setField1(FiniteValue field1) {
    this.field1 = field1;
  }

  public FiniteValue getField2() {
    return field2;
  }

  public void setField2(FiniteValue field2) {
    this.field2 = field2;
  }

  public FiniteValue getField3() {
    return field3;
  }

  public void setField3(FiniteValue field3) {
    this.field3 = field3;
  }

  public FiniteValue getField4() {
    return field4;
  }

  public void setField4(FiniteValue field4) {
    this.field4 = field4;
  }

  public FiniteValue getField5() {
    return field5;
  }

  public void setField5(FiniteValue field5) {
    this.field5 = field5;
  }

  public String getDateFormat() {
    return dateFormat;
  }

  public void setDateFormat(String dateFormat) {
    this.dateFormat = dateFormat;
  }

  public Boolean getResetSeqPerDay() {
    return resetSeqPerDay;
  }

  public void setResetSeqPerDay(Boolean resetSeqPerDay) {
    this.resetSeqPerDay = resetSeqPerDay;
  }

  public Integer getSequenceWidth() {
    return sequenceWidth;
  }

  public void setSequenceWidth(Integer sequenceWidth) {
    this.sequenceWidth = sequenceWidth;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }
}
