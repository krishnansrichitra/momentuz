package com.momentus.foundation.common.repository;

import com.momentus.foundation.common.nextup.model.NextUpConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NextUpConfigRepository extends JpaRepository<NextUpConfig, String> {

  NextUpConfig findByProfile_ProfileCodeAndEntity(String profileCode, String entity);
}
