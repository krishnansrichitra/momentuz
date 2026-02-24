package com.momentus.projmanagement.basedata.client.model;

import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.common.model.Address;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "clients")
public class Client extends OrgBasedEntity {

    @Column
            @EntityProperties(isBK = true)
    String title;

    @Embedded
    Address address;

    @Column String primaryContactName;

    @Column String primaryContactPhone;

    @Column String secondaryContactName;

    @Column String secondaryContactPhone;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPrimaryContactName() {
        return primaryContactName;
    }

    public void setPrimaryContactName(String primaryContactName) {
        this.primaryContactName = primaryContactName;
    }

    public String getPrimaryContactPhone() {
        return primaryContactPhone;
    }

    public void setPrimaryContactPhone(String primaryContactPhone) {
        this.primaryContactPhone = primaryContactPhone;
    }

    public String getSecondaryContactName() {
        return secondaryContactName;
    }

    public void setSecondaryContactName(String secondaryContactName) {
        this.secondaryContactName = secondaryContactName;
    }

    public String getSecondaryContactPhone() {
        return secondaryContactPhone;
    }

    public void setSecondaryContactPhone(String secondaryContactPhone) {
        this.secondaryContactPhone = secondaryContactPhone;
    }

    @Override
    public Map<String, Object> getBK() {
        Map<String, Object> result = new HashMap<>();
        result.put("title", title);
        return result;
    }

    @Override
    public void setBK(Object object) {
        setTitle(String.valueOf(object));
    }

    @Override
    public String getBKField() {
        return "title";
    }

    @Override
    public Object getBKValue() {
        return title;
    }


}
