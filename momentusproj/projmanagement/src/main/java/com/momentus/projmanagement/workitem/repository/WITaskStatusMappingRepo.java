package com.momentus.projmanagement.workitem.repository;

import com.momentus.projmanagement.workitem.model.WITaskStatusMapping;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WITaskStatusMappingRepo extends JpaRepository<WITaskStatusMapping, Long> {

  List<WITaskStatusMapping> findByTypeAndProfile_ProfileCodeInOrderBySeqAsc(
      String type, Collection<String> profileCodes);
}
