package com.momentus.foundation.organization.service;

import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.generic.dao.GenericDAO;
import com.momentus.foundation.organization.model.OrgProfile;
import com.momentus.foundation.organization.model.Organization;
import com.momentus.foundation.organization.repository.OrgProfileRepository;
import com.momentus.foundation.profile.model.Profile;
import com.momentus.foundation.profile.model.ProfileGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrgProfileService {

    @Autowired
    OrgProfileRepository orgProfileRepository;

    @Autowired
    OrganizationService organizationService ;

    @Autowired
    GenericDAO genericDAO;



    public TransactionResponse save(OrgProfile orgProfile, ApplicationContext context)
    {
        if (orgProfile.getOrgId() != null ) {
            Organization organization = organizationService.getOrgById(orgProfile.getOrgId().getId());
            orgProfile.setOrgId(organization);
        }
        if (orgProfile.getProfile() !=null){
            Profile profile = (Profile)genericDAO.loadById(orgProfile.getProfile());
            orgProfile.setProfile(profile);
        }
        if(orgProfile.getProfileGroup() != null ){
            ProfileGroup profileGroup = (ProfileGroup)  genericDAO.loadById(orgProfile.getProfileGroup());
            orgProfile.setProfileGroup(profileGroup);
         }
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

    public  OrgProfile getProfileForGroup(String profileGroupCode, Long orgId)
    {
        OrgProfile orgProfile = orgProfileRepository.findByProfileGroup_ProfileGroupCodeAndOrgId_Id(profileGroupCode,orgId);
        return orgProfile;
    }
}
