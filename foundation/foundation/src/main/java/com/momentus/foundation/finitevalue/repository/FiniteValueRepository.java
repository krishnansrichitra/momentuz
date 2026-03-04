package com.momentus.foundation.finitevalue.repository;

import com.momentus.foundation.finitevalue.model.FiniteValue;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FiniteValueRepository extends JpaRepository<FiniteValue, String> {

  List<FiniteValue> findByFiniteGroup_GroupCodeOrderBySeqNoAsc(String groupCode);

  List<FiniteValue> findByFiniteGroup_GroupCodeAndProfile_ProfileCodeInOrderBySeqNoAsc(
      String groupCode, Collection<String> profileCodes);
}
