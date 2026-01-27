package com.momentus.foundation.orgsignup.repository;

import com.momentus.foundation.orgsignup.model.SectorProfileData;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SectorProfileRepository extends JpaRepository<SectorProfileData, String> {
  List<SectorProfileData> findBySector(String sector);
}
