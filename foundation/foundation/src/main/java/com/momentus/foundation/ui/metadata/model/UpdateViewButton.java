package com.momentus.foundation.ui.metadata.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "updateview_buttons")
public class UpdateViewButton {

  @Id String id;

  @ManyToOne()
  @JoinColumn(name = "updateview_metadata_id", referencedColumnName = "id", nullable = true)
  @JsonIgnore
  UpdateViewMetadata updateViewMetadata;

  @Column String buttonClass;

  @Column String jsMethod;

  @Column String innerText;

  @Column String visibility;

  @Column(name = "seq_no")
  BigDecimal seqNo;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public UpdateViewMetadata getUpdateViewMetadata() {
    return updateViewMetadata;
  }

  public void setUpdateViewMetadata(UpdateViewMetadata updateViewMetadata) {
    this.updateViewMetadata = updateViewMetadata;
  }

  public String getButtonClass() {
    return buttonClass;
  }

  public void setButtonClass(String buttonClass) {
    this.buttonClass = buttonClass;
  }

  public String getJsMethod() {
    return jsMethod;
  }

  public void setJsMethod(String jsMethod) {
    this.jsMethod = jsMethod;
  }

  public String getInnerText() {
    return innerText;
  }

  public void setInnerText(String innerText) {
    this.innerText = innerText;
  }

  public BigDecimal getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(BigDecimal seqNo) {
    this.seqNo = seqNo;
  }

  public String getVisibility() {
    return visibility;
  }

  public void setVisibility(String visibility) {
    this.visibility = visibility;
  }
}
