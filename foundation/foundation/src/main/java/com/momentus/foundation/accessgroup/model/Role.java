package com.momentus.foundation.accessgroup.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.persistence.*;

import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Table(name = "roles")
public class Role extends OrgBasedEntity {


    @Column
    @EntityProperties(isBK = true  )
    String description;


    @Column( columnDefinition = "BLOB")
    String accessCodes;



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccessCodes() {
        return accessCodes;
    }

    public void setAccessCodes(String accessCodes) {
        this.accessCodes = accessCodes;
    }

    @Override
    @JsonIgnore
    public Map<String, Object> getBK() {
        Map<String,Object> bkMap = new LinkedHashMap<>();
        bkMap.put("description",this.description);
        return bkMap;
    }

    @Override
    public String getBKField() {
        return "description";
    }
}
