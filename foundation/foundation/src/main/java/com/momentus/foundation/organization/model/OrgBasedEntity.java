package com.momentus.foundation.organization.model;

import com.momentus.foundation.common.model.BaseEntity;
import jakarta.persistence.*;

@MappedSuperclass
public class OrgBasedEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "org_id", referencedColumnName = "Id")
    Organization orgId;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
}
