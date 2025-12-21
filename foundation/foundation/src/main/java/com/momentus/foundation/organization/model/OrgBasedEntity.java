package com.momentus.foundation.organization.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.momentus.corefw.data.EntityProperties;
import com.momentus.foundation.common.model.BaseEntity;
import jakarta.persistence.*;

@MappedSuperclass
public class OrgBasedEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", referencedColumnName = "Id")
    Organization orgId;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EntityProperties(isPK = true)
    Long id;

    public Organization getOrgId() {
        return orgId;
    }

    public void setOrgId(Organization orgId) {
        this.orgId = orgId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    @JsonIgnore
    public Object getPK() {
        return id;
    }
}
