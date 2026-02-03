package com.momentus.foundation.orgsignup.service;

import com.momentus.foundation.accessgroup.model.Role;
import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.accessgroup.model.UserRoles;
import com.momentus.foundation.accessgroup.repository.UserRepository;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.generic.service.GenericService;
import com.momentus.foundation.login.service.AppUserDetailsService;
import com.momentus.foundation.organization.model.OrgProfile;
import com.momentus.foundation.organization.model.Organization;
import com.momentus.foundation.organization.model.Sector;
import com.momentus.foundation.organization.repository.SectorRepository;
import com.momentus.foundation.organization.service.OrgProfileService;
import com.momentus.foundation.organization.service.OrganizationService;
import com.momentus.foundation.orgsignup.dto.OrgSignupDTO;
import com.momentus.foundation.orgsignup.model.PrimaryUserRole;
import com.momentus.foundation.orgsignup.model.SectorProfileData;
import com.momentus.foundation.orgsignup.repository.PrimaryUserRoleRepository;
import com.momentus.foundation.orgsignup.repository.SectorProfileRepository;
import com.momentus.foundation.profile.model.Profile;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class OrgSignupService {

  @Autowired OrganizationService organizationService;

  @Autowired OrgProfileService orgProfileService;

  @Autowired SectorProfileRepository sectorProfileRepository;

  @Autowired AppUserDetailsService appUserDetailsService;

  @Autowired UserRepository userRepository;

  @Autowired PrimaryUserRoleRepository primaryUserRoleRepository;

  @Autowired GenericService genericService;

  @Autowired SectorRepository sectorRepository;

  public Map<String, String> getAllSectorsforUI() {
    Map<String, String> result = new LinkedHashMap<>();
    List<Sector> sectors = sectorRepository.findAll();
    for (Sector sector : sectors) {
      result.put(sector.getCode(), sector.getName());
    }
    return result;
  }

  public TransactionResponse orgSignup(OrgSignupDTO orgSignupDTO, ApplicationContext context) {
    Organization organization = createOrgFromDTO(orgSignupDTO);
    TransactionResponse transactionResponse =
        organizationService.saveOrganization(organization, context);
    if (transactionResponse.hasHardError()) {
      return transactionResponse;
    }

    organization = organizationService.getOrgByOrgCode(organization.getOrgCode());
    Profile profile = null;
    List<SectorProfileData> sectorProfileDatas =
        sectorProfileRepository.findBySector(orgSignupDTO.getSector());
    if (!CollectionUtils.isEmpty(sectorProfileDatas)) {
      OrgProfile orgProfile = new OrgProfile();
      profile = sectorProfileDatas.get(0).getProfile();
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
    user.setPassword(
        appUserDetailsService.makePasswordForPrimaryUser(orgSignupDTO.getPrimaryUserEmail()));
    user.setUserId(orgSignupDTO.getPrimaryUserEmail());
    userRepository.save(user);

    if (profile != null) {
      UserRoles userRoles = new UserRoles();
      List<PrimaryUserRole> primaryUserRoles =
          primaryUserRoleRepository.findByProfileProfileCode(profile.getProfileCode());
      if (!CollectionUtils.isEmpty(primaryUserRoles)) {
        PrimaryUserRole primaryUserRole = primaryUserRoles.get(0);
        Role role = new Role();
        role.setDescription(primaryUserRole.getRoleDescription());
        role.setAccessCodes(primaryUserRole.getAccessCodes());
        role.setOrgId(organization);
        genericService.saveEntity(role, context);
        role = (Role) genericService.findByBusinessKey(role.getBK(), Role.class, context);
        userRoles.setUser(user);
        userRoles.setRole(role);
        genericService.saveEntity(userRoles, context);
      }
    }
    return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
  }

  private Organization createOrgFromDTO(OrgSignupDTO orgSignupDTO) {
    Organization organization = new Organization();
    organization.setOrgCode(orgSignupDTO.getOrgCode());
    organization.setOrganizationName(orgSignupDTO.getOrganizationName());
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
