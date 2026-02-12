package com.momentus.foundation.common.repository;

import com.momentus.foundation.common.nextup.model.NextUpData;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NextUpDataRepository extends JpaRepository<NextUpData, Long> {

  List<NextUpData> findByConfig_EntityAndOrgId_IdAndComponent1AndComponent2AndComponent3(
      String entity, Long orgId, String component1, String component2, String component3);

  @Modifying
  @Transactional
  @Query("update NextUpData n set n.lastSeqValue = ?2 , n.lastDate= ?3 where n.id = ?1")
  public void updateNextUpCounter(Long id, Long newValue, LocalDate date);
}
