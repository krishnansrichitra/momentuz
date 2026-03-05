package com.momentus.projmanagement.workitem.repository;

import com.momentus.projmanagement.workitem.model.WorkItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkItemRepository extends JpaRepository<WorkItem, Long> {}
