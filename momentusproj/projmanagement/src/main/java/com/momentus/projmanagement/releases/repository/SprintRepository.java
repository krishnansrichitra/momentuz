package com.momentus.projmanagement.releases.repository;

import com.momentus.foundation.generic.service.GenericService;
import com.momentus.projmanagement.releases.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SprintRepository extends JpaRepository<Sprint,Long> {

}
