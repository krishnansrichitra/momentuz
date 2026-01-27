package com.momentus.foundation.orgsignup.repository;

import com.momentus.foundation.orgsignup.model.PrimaryUserRole;
import com.momentus.foundation.orgsignup.model.SectorProfileData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrimaryUserRoleRepository extends JpaRepository<PrimaryUserRole,String> {

    List<PrimaryUserRole> findByProfileId(Long profileId);
}
