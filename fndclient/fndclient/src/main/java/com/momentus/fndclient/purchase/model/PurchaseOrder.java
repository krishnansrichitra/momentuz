package com.momentus.fndclient.purchase.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.corefw.data.EntityProperties;
import com.momentus.fndclient.supplier.model.Supplier;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Purchase")
public class PurchaseOrder extends OrgBasedEntity {

  @EntityProperties(isBK = true)
  String docNumber;

  String billNo;

  LocalDate purchaseDate;

  LocalDate expectedDeliveryDate;

  LocalDate actualDeliveryDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "supplier_id", referencedColumnName = "id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  Supplier supplier;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "po_status", referencedColumnName = "fv_code")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  FiniteValue status;

  @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("id Asc")
  List<POLine> poLines;

  String comments;

  @Column BigDecimal grossTotal;

  @Column BigDecimal taxAmount;

  @Column BigDecimal total;

  public String getDocNumber() {
    return docNumber;
  }

  public void setDocNumber(String docNumber) {
    this.docNumber = docNumber;
  }

  public String getBillNo() {
    return billNo;
  }

  public void setBillNo(String billNo) {
    this.billNo = billNo;
  }

  public LocalDate getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(LocalDate purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public LocalDate getExpectedDeliveryDate() {
    return expectedDeliveryDate;
  }

  public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
    this.expectedDeliveryDate = expectedDeliveryDate;
  }

  public LocalDate getActualDeliveryDate() {
    return actualDeliveryDate;
  }

  public void setActualDeliveryDate(LocalDate actualDeliveryDate) {
    this.actualDeliveryDate = actualDeliveryDate;
  }

  public Supplier getSupplier() {
    return supplier;
  }

  public void setSupplier(Supplier supplier) {
    this.supplier = supplier;
  }

  public FiniteValue getStatus() {
    return status;
  }

  public void setStatus(FiniteValue status) {
    this.status = status;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public List<POLine> getPoLines() {
    return poLines;
  }

  public void setPoLines(List<POLine> poLines) {
    this.poLines = poLines;
  }

  public BigDecimal getGrossTotal() {
    return grossTotal;
  }

  public void setGrossTotal(BigDecimal grossTotal) {
    this.grossTotal = grossTotal;
  }

  public BigDecimal getTaxAmount() {
    return taxAmount;
  }

  public void setTaxAmount(BigDecimal taxAmount) {
    this.taxAmount = taxAmount;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }
}
