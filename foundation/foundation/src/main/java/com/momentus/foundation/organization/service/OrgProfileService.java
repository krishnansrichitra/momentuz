package com.momentus.foundation.organization.service;

import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.generic.dao.GenericDAO;
import com.momentus.foundation.organization.model.OrgProfile;
import com.momentus.foundation.organization.model.Organization;
import com.momentus.foundation.organization.repository.OrgProfileRepository;
import com.momentus.foundation.profile.model.Profile;
import com.momentus.foundation.profile.model.ProfileGroup;
import java.time.LocalDateTime;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrgProfileService {

  @Autowired OrgProfileRepository orgProfileRepository;

  @Autowired OrganizationService organizationService;

  @Autowired GenericDAO genericDAO;

  @Autowired GeneralMessages generalMessages;

  public TransactionResponse save(OrgProfile orgProfile, ApplicationContext context) {
    if (context.getOrganization().getId() != 1) {
      MomentusError momentusError =
          new MomentusError(
              GeneralMessages.UNAUTHORIZED_SYSTEM_OPERATION,
              generalMessages.getMessage(
                  GeneralMessages.UNAUTHORIZED_SYSTEM_OPERATION, context.getLocale()));
      return new TransactionResponse(
          TransactionResponse.RESPONSE_STATUS.FAILURE, Arrays.asList(momentusError), orgProfile);
    }

    String profileGroupCode = null;
    Profile newProfile = null;
    if (orgProfile.getProfileGroup() != null) {
      ProfileGroup profileGroup = (ProfileGroup) genericDAO.loadById(orgProfile.getProfileGroup());
      orgProfile.setProfileGroup(profileGroup);
      profileGroupCode = profileGroup.getProfileGroupCode();
    }
    if (orgProfile.getOrgId() != null) {
      Organization organization = organizationService.getOrgById(orgProfile.getOrgId().getId());
      orgProfile.setOrgId(organization);
    }
    if (orgProfile.getProfile() != null) {
      newProfile = (Profile) genericDAO.loadById(orgProfile.getProfile());
      orgProfile.setProfile(newProfile);
    }

    OrgProfile curprofile =
        orgProfileRepository.findByProfileGroup_ProfileGroupCodeAndOrgId_Id(
            profileGroupCode, orgProfile.getOrgId().getId());
    if (curprofile != null) {
      curprofile.setProfile(newProfile);
      curprofile.setLastUpdatedTime(LocalDateTime.now());
      curprofile.setLastUpdatedBy(context.getLoggedInUser());
      orgProfileRepository.save(curprofile);
      return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);

    } else {
      if (orgProfile.getId() == null) {
        orgProfile.setCreatedBy(context.getLoggedInUser());
        orgProfile.setCreatedTime(LocalDateTime.now());
      }
      orgProfile.setLastUpdatedBy(context.getLoggedInUser());
      orgProfile.setLastUpdatedTime(LocalDateTime.now());
      orgProfileRepository.save(orgProfile);
      return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
    }
  }

  public OrgProfile getById(Long id, ApplicationContext context) {
    OrgProfile orgProfile = orgProfileRepository.findById(id).get();
    return orgProfile;
  }

  public OrgProfile getProfileForGroup(String profileGroupCode, Long orgId) {
    OrgProfile orgProfile =
        orgProfileRepository.findByProfileGroup_ProfileGroupCodeAndOrgId_Id(
            profileGroupCode, orgId);
    return orgProfile;
  }
}
