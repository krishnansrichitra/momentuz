package com.momentus.foundation.finitevalue.repository;

import com.momentus.foundation.finitevalue.model.FiniteValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FiniteValueRepository extends JpaRepository<FiniteValue,String> {

    List<FiniteValue> findByFiniteGroup_GroupCode(String groupCode);

}
