package com.momentus.foundation.ui.metadata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "list_columns")
public class ListColumn {
  @Id String id;

  @ManyToOne()
  @JoinColumn(name = "list_metadata_id", referencedColumnName = "id", nullable = true)
  @JsonIgnore
  ListMetadata listMetadata;

  @Column String fieldKey;

  @Column String accessor;

  @Column(name = "seq_no")
  BigDecimal seqNo;

  public ListMetadata getListMetadata() {
    return listMetadata;
  }

  public void setListMetadata(ListMetadata listMetadata) {
    this.listMetadata = listMetadata;
  }

  public String getFieldKey() {
    return fieldKey;
  }

  public void setFieldKey(String fieldKey) {
    this.fieldKey = fieldKey;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
}
