package com.momentus.foundation.orgsignup.repository;

import com.momentus.foundation.orgsignup.model.PrimaryUserRole;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrimaryUserRoleRepository extends JpaRepository<PrimaryUserRole, String> {

  List<PrimaryUserRole> findByProfileId(Long profileId);
}
