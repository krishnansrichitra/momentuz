package com.momentus.foundation.common.service;

import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.nextup.model.NextUpConfig;
import com.momentus.foundation.common.nextup.model.NextUpData;
import com.momentus.foundation.common.repository.NextUpConfigRepository;
import com.momentus.foundation.common.repository.NextUpDataRepository;
import com.momentus.foundation.finitevalue.model.FiniteValue;
import com.momentus.foundation.organization.model.OrgProfile;
import com.momentus.foundation.organization.service.OrgProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class NextUpService {

    @Autowired
    OrgProfileService orgProfileService;

    @Autowired
    NextUpConfigRepository nextUpConfigRepository;

    @Autowired
    NextUpDataRepository nextUpDataRepository;
    public void getNextUpNo(ApplicationContext context, String entity )
    {
        OrgProfile orgProfile = orgProfileService.getProfileForGroup("GNL", context.getOrganization().getId());
        List<NextUpData> nextUpDataList = nextUpDataRepository.finByEntityAndOrgId(entity,context.getOrganization().getId());
        if (CollectionUtils.isEmpty(nextUpDataList)  ) {
            NextUpConfig config = nextUpConfigRepository.findByProfile_ProfileCodeAndEntity(orgProfile.getProfile().getProfileCode(), entity);

        }else {
            NextUpData nextUpData =   nextUpDataList.get(0);

        }

    }

    public String getNexUpNumber(NextUpConfig nextUpConfig,NextUpData nextUpData){
        return "";
    }


    private String getFieldValue(FiniteValue finiteValue){
        if ("nxtup_dt".equalsIgnoreCase(finiteValue.getFvCode()))
        {

        }
        if ("nxtup_prfx".equalsIgnoreCase(finiteValue.getFvCode()))
        {

        }
        if ("nxtup_seq".equalsIgnoreCase(finiteValue.getFvCode()))
        {

        }
        if ("nxtup_BK".equalsIgnoreCase(finiteValue.getFvCode()))
        {

        }
        return ";";

    }




}


