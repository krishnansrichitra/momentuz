package com.momentus.fndclient.supplier.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.common.model.Address;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "suppliers")
public class Supplier extends OrgBasedEntity {


    @EntityProperties(isBK = true)
    @Column
    String supplierName;


    @Embedded
    Address address;

    @Column
    BigDecimal creditLimit;

    @Column
    LocalDateTime joiningDate ;

    @Column
    Integer supplierRank;

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

    @Override
    @JsonIgnore
    public Map<String, Object> getBK() {
        Map<String,Object> objectMap = new HashMap<>();
        objectMap.put("supplierName",supplierName);
        return objectMap;
    }


    @Override
    @JsonIgnore
    public String getBKField() {
        return "supplierName";
    }

    @JsonIgnore
    @Override
    public void setBK(Object object) {
        setSupplierName(String.valueOf(object));
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public LocalDateTime getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDateTime joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Integer getSupplierRank() {
        return supplierRank;
    }

    public void setSupplierRank(Integer supplierRank) {
        this.supplierRank = supplierRank;
    }
}

