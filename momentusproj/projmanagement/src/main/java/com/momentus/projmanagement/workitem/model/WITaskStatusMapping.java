package com.momentus.projmanagement.workitem.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.profile.model.ProfileBasedEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "wi_task_status_mapping")
public class WITaskStatusMapping extends ProfileBasedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "wi_type", referencedColumnName = "fv_code")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  FiniteValue type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "wi_status", referencedColumnName = "fv_code")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  FiniteValue status;

  @Column Float seq;

  @Column String comments;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public FiniteValue getType() {
    return type;
  }

  public void setType(FiniteValue type) {
    this.type = type;
  }

  public FiniteValue getStatus() {
    return status;
  }

  public void setStatus(FiniteValue status) {
    this.status = status;
  }

  public Float getSeq() {
    return seq;
  }

  public void setSeq(Float seq) {
    this.seq = seq;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }
}
