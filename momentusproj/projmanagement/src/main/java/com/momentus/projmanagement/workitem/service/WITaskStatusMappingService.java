package com.momentus.projmanagement.workitem.service;


import com.momentus.foundation.common.Utils;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.organization.model.OrgProfile;
import com.momentus.foundation.organization.service.OrgProfileService;
import com.momentus.projmanagement.workitem.model.WITaskStatusMapping;
import com.momentus.projmanagement.workitem.repository.WITaskStatusMappingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WITaskStatusMappingService {

    @Autowired
    WITaskStatusMappingRepo wiTaskStatusMappingRepo;

    @Autowired
    OrgProfileService orgProfileService;


    public List<FiniteValue> getPermissibleTaskStatus(Long orgId ,String taskType) {
        OrgProfile orgProfile = orgProfileService.getProfileForGroup("GNL", orgId);
        if (orgProfile != null) {
            List<WITaskStatusMapping> taskStatusMappings = wiTaskStatusMappingRepo.findByTypeAndProfile_ProfileCodeInOrderBySeqAsc(taskType,
                    Utils.getProfileCodes(orgProfile.getProfile().getFullProfileCode()));
            if (!CollectionUtils.isEmpty(taskStatusMappings)) {
                return taskStatusMappings.stream().map(WITaskStatusMapping::getStatus).collect(Collectors.toList());
            }

        }
        return new ArrayList<>();
    }
}
