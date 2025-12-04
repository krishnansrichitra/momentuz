package com.momentus.foundation.accessgroup.model;

import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends OrgBasedEntity {


    @Column
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


}
