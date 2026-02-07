package com.momentus.foundation.common.repository;

import com.momentus.foundation.common.nextup.model.NextUpData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NextUpDataRepository extends JpaRepository<NextUpData,Long> {

    List<NextUpData> finByEntityAndOrgId(String entity, Long orgId);
}
