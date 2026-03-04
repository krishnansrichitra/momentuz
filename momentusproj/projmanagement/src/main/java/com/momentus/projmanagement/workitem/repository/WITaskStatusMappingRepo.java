package com.momentus.projmanagement.workitem.repository;

import com.momentus.projmanagement.workitem.model.WITaskStatusMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface WITaskStatusMappingRepo extends JpaRepository<WITaskStatusMapping,Long> {

    List<WITaskStatusMapping> findByTypeAndProfile_ProfileCodeInOrderBySeqAsc(String type,  Collection<String> profileCodes);


}
