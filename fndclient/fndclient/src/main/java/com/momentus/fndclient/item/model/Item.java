package com.momentus.fndclient.item.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.corefw.data.EntityProperties;
import com.momentus.fndclient.supplier.model.Supplier;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.persistence.*;

@Entity
@Table(name="items")
public class Item extends OrgBasedEntity {


   @Column
   @EntityProperties(isBK = true)
   String itemName;

    @Column
    @EntityProperties(isMandatory = true)
    String barcode;

    @Column
    @EntityProperties(isMandatory = true)
    String itemCode;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "uom_type",referencedColumnName = "fv_code")
   @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
   FiniteValue uomType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_group",referencedColumnName = "fv_code")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    FiniteValue itemGroup;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "supplier_id" , referencedColumnName = "id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Supplier supplier;


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public FiniteValue getUomType() {
        return uomType;
    }

    public void setUomType(FiniteValue uomType) {
        this.uomType = uomType;
    }

    public FiniteValue getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(FiniteValue itemGroup) {
        this.itemGroup = itemGroup;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
