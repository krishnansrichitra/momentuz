package com.momentus.fndclient.purchase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.momentus.foundation.common.model.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "po_supplier_info")
public class POSupplierInfo extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "purchase_id", nullable = true)
  @JsonIgnore
  PurchaseOrder purchase;

  @Column LocalDate productionDate;

  @Column String supplierComments;

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

  public LocalDate getProductionDate() {
    return productionDate;
  }

  public void setProductionDate(LocalDate productionDate) {
    this.productionDate = productionDate;
  }

  public String getSupplierComments() {
    return supplierComments;
  }

  public void setSupplierComments(String supplierComments) {
    this.supplierComments = supplierComments;
  }

    @Override
    public void setParentObject(BaseEntity base) {
        setPurchase((PurchaseOrder) base);
    }
}
