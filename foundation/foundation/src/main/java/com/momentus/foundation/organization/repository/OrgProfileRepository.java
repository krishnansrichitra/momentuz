package com.momentus.foundation.organization.repository;

import com.momentus.foundation.organization.model.OrgProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgProfileRepository extends JpaRepository<OrgProfile,Long> {

    public OrgProfile findByProfileGroupCodeAndOrgId(String profileGroupCode, Long orgId);
}
