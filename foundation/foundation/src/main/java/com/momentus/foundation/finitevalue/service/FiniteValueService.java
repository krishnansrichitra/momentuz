package com.momentus.foundation.finitevalue.service;

import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.finitevalue.repository.FiniteValueRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FiniteValueService {

  @Autowired FiniteValueRepository finiteValueRepository;

  public FiniteValue getFinitieValueByCode(String code) {
    return finiteValueRepository.findById(code).orElse(null);
  }

  public List<FiniteValue> getFiniteValueByGroup(String group) {
    return finiteValueRepository.findByFiniteGroup_GroupCodeOrderBySeqNoAsc(group);
  }
}
