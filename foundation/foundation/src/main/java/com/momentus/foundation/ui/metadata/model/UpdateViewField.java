package com.momentus.foundation.ui.metadata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "updateview_fields")
public class UpdateViewField {

  @Id String id;

  @Column String fieldKey;

  @Column String control;

  @Column String param;

  @Column String accessor;

  @Column BigDecimal seqNo;

  @Column String dataType;

  @Column String parent;

  @ManyToOne()
  @JoinColumn(name = "updateview_metadata_id", referencedColumnName = "id", nullable = true)
  @JsonIgnore
  UpdateViewMetadata updateViewMetadata;

  @Column String visibility;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFieldKey() {
    return fieldKey;
  }

  public void setFieldKey(String fieldKey) {
    this.fieldKey = fieldKey;
  }

  public String getControl() {
    return control;
  }

  public void setControl(String control) {
    this.control = control;
  }

  public String getParam() {
    return param;
  }

  public void setParam(String param) {
    this.param = param;
  }

  public String getAccessor() {
    return accessor;
  }

  public void setAccessor(String accessor) {
    this.accessor = accessor;
  }

  public BigDecimal getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(BigDecimal seqNo) {
    this.seqNo = seqNo;
  }

  public UpdateViewMetadata getUpdateViewMetadata() {
    return updateViewMetadata;
  }

  public void setUpdateViewMetadata(UpdateViewMetadata updateViewMetadata) {
    this.updateViewMetadata = updateViewMetadata;
  }

  public String getVisibility() {
    return visibility;
  }

  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }

  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public String getParent() {
    return parent;
  }

  public void setParent(String parent) {
    this.parent = parent;
  }
}
