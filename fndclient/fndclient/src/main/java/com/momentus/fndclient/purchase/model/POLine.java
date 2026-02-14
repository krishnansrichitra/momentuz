package com.momentus.fndclient.purchase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.fndclient.item.model.Item;
import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "purchase_lines")
public class POLine extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "purchase_id", nullable = true)
  @JsonIgnore
  PurchaseOrder purchase;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  Item item;

  @Column Integer qty;

  @Column BigDecimal price;

  @Column BigDecimal total;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "line_status", referencedColumnName = "fv_code")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  FiniteValue lineStatus;

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

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public Integer getQty() {
    return qty;
  }

  public void setQty(Integer qty) {
    this.qty = qty;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public BigDecimal getTotal() {
    return total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public FiniteValue getLineStatus() {
    return lineStatus;
  }

  public void setLineStatus(FiniteValue lineStatus) {
    this.lineStatus = lineStatus;
  }

  @Override
  public void setParentObject(BaseEntity base) {
    setPurchase((PurchaseOrder) base);
  }

    @Override
    public Object getPK() {
        return   id;
    }


}
