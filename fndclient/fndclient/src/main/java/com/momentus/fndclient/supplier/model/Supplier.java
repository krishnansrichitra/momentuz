package com.momentus.fndclient.supplier.model;

import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.common.model.Address;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "suppliers")
public class Supplier extends OrgBasedEntity {


    @EntityProperties(isBK = true)
    @Column
    String supplierName;


    @Embedded
    Address address;


    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
