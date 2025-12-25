package com.momentus.foundation.organization.repository;

import com.momentus.foundation.organization.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization,Long> {

    Organization findByOrgCode(String orgCode);

}


