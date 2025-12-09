package com.momentus.foundation.entity.repository;

import com.momentus.foundation.entity.model.Entity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityRepository extends JpaRepository<Entity,String> {
}
