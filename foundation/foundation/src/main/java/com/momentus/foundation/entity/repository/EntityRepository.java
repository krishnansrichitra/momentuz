package com.momentus.foundation.entity.repository;

import com.momentus.foundation.entity.model.Entity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EntityRepository extends JpaRepository<Entity, String> {

  @Query(" select e from Entity e where e.supportImport = true order by e.sequence asc")
  public List<Entity> findImportableEnitites();
}
