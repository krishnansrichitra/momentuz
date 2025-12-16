package com.momentus.foundation.organization.service;

import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.organization.model.OrgProfile;
import com.momentus.foundation.organization.repository.OrgProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrgProfileService {

    @Autowired
    OrgProfileRepository orgProfileRepository;


    public TransactionResponse save(OrgProfile orgProfile, ApplicationContext context)
    {
        if (orgProfile.getId() == null) {
            orgProfile.setCreatedBy(context.getLoggedInUser());
            orgProfile.setCreatedTime(LocalDateTime.now());
        }
        orgProfile.setLastUpdatedBy(context.getLoggedInUser());
        orgProfile.setLastUpdatedTime(LocalDateTime.now());
        orgProfileRepository.save(orgProfile);
        return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
    }

    public OrgProfile getById(Long id, ApplicationContext context)
    {
        OrgProfile orgProfile = orgProfileRepository.findById(id).get();
        return  orgProfile;
    }
}
