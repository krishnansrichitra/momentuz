package com.momentus.foundation.organization.model;


import jakarta.persistence.*;

@Entity
@Table(name = "org_profile")
public class OrgProfile {

    @Id
    Long id;

    @ManyToOne(fetch = FetchType.EAGER , optional = false  )
    Organization organization;

    


}
