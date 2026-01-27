package com.momentus.foundation.orgsignup.service;

import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.organization.model.OrgProfile;
import com.momentus.foundation.organization.model.Organization;
import com.momentus.foundation.organization.model.Sector;
import com.momentus.foundation.organization.service.OrgProfileService;
import com.momentus.foundation.organization.service.OrganizationService;
import com.momentus.foundation.orgsignup.dto.OrgSignupDTO;
import com.momentus.foundation.orgsignup.model.SectorProfileData;
import com.momentus.foundation.orgsignup.repository.SectorProfileRepository;
import com.momentus.foundation.profile.model.Profile;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class OrgSignupService {

  @Autowired OrganizationService organizationService;

  @Autowired OrgProfileService orgProfileService;

  @Autowired SectorProfileRepository sectorProfileRepository;

  public TransactionResponse orgSignup(OrgSignupDTO orgSignupDTO, ApplicationContext context) {
    Organization organization = createOrgFromDTO(orgSignupDTO);
    TransactionResponse transactionResponse =
        organizationService.saveOrganization(organization, context);
    if (transactionResponse.hasHardError()) {
      return transactionResponse;
    }

    organization = organizationService.getOrgByOrgCode(organization.getOrgCode());
    List<SectorProfileData> sectorProfileDatas =
        sectorProfileRepository.findBySector(orgSignupDTO.getSector());
    if (!CollectionUtils.isEmpty(sectorProfileDatas)) {
      OrgProfile orgProfile = new OrgProfile();
      Profile profile = sectorProfileDatas.get(0).getProfile();
      orgProfile.setProfileGroup(profile.getProfileGroup());
      orgProfile.setProfile(profile);
      orgProfile.setOrgId(organization);
      orgProfileService.save(orgProfile, context);
    }

    User user = new User();
    user.setOrgId(organization);
    user.setSystemCreated(true);
    user.setFirstName(orgSignupDTO.getFirstName());
    user.setLastName(orgSignupDTO.getLastName());
    user.setPhone(orgSignupDTO.getPhone());
    user.setEmail(orgSignupDTO.getEmail());
    user.setOrgOwner(true);
    //user.setPassword();
    // user.getFirstName(orgSignupDTO.)

    // Save Roles

    return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
  }

  private Organization createOrgFromDTO(OrgSignupDTO orgSignupDTO) {
    Organization organization = new Organization();
    organization.setOrgCode(orgSignupDTO.getOrgCode());
    organization.setOrganizationName(organization.getOrganizationName());
    organization.setRegistrationDate(LocalDate.now());
    organization.setAddress1(orgSignupDTO.getAddress1());
    organization.setAddress2(orgSignupDTO.getAddress2());
    organization.setCity(orgSignupDTO.getCity());
    organization.setEmail(orgSignupDTO.getEmail());
    organization.setPhone(orgSignupDTO.getPhone());
    organization.setState(orgSignupDTO.getState());
    organization.setCountry(orgSignupDTO.getCountry());
    Sector sector = new Sector();
    sector.setCode(orgSignupDTO.getSector());
    organization.setSector(sector);
    organization.setActive(true);
    return organization;
  }
}
