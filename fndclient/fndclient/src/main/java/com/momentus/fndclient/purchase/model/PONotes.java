package com.momentus.fndclient.purchase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import jakarta.persistence.*;

@Entity
@Table(name = "purchase_notes")
public class PONotes extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "purchase_id", nullable = true)
  @JsonIgnore
  PurchaseOrder purchase;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "note_type", referencedColumnName = "fv_code")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  FiniteValue noteType;

  @Column String note;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public PurchaseOrder getPurchase() {
    return purchase;
  }

  public void setPurchase(PurchaseOrder purchase) {
    this.purchase = purchase;
  }

  public FiniteValue getNoteType() {
    return noteType;
  }

  public void setNoteType(FiniteValue noteType) {
    this.noteType = noteType;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  @Override
  public void setParentObject(BaseEntity base) {
    setPurchase((PurchaseOrder) base);
  }

  @Override
  public Object getPK() {
    return id;
  }
}
