package com.momentus.foundation.finitevalue.service;

import com.momentus.foundation.common.Utils;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.finitevalue.repository.FiniteValueRepository;
import com.momentus.foundation.organization.model.OrgProfile;
import com.momentus.foundation.organization.service.OrgProfileService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class FiniteValueService {

  @Autowired FiniteValueRepository finiteValueRepository;

  @Autowired OrgProfileService orgProfileService;

  public FiniteValue getFinitieValueByCode(String code) {
    return finiteValueRepository.findById(code).orElse(null);
  }

  public List<FiniteValue> getFiniteValueByGroup(String group) {
    return finiteValueRepository.findByFiniteGroup_GroupCodeOrderBySeqNoAsc(group);
  }

  @Cacheable("FVValuesByOrgId")
  public List<FiniteValue> getFiniteValueByGroupAndOrg(String group, Long orgId) {
    OrgProfile orgProfile = orgProfileService.getProfileForGroup("GNL", orgId);
    if (orgProfile != null) {
      List<String> profileCodes =
          Utils.getProfileCodes(orgProfile.getProfile().getFullProfileCode());
      return finiteValueRepository
          .findByFiniteGroup_GroupCodeAndProfile_ProfileCodeInOrderBySeqNoAsc(group, profileCodes);
    } else {
      return finiteValueRepository.findByFiniteGroup_GroupCodeOrderBySeqNoAsc(group);
    }
  }
}
