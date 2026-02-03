package com.momentus.foundation.orgsignup.service;

import com.momentus.foundation.accessgroup.model.Role;
import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.accessgroup.model.UserRoles;
import com.momentus.foundation.accessgroup.repository.UserRepository;
import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.MomentusError;
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
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

  @Autowired GeneralMessages generalMessages;

  private static final Logger log = LoggerFactory.getLogger(OrgSignupService.class);

  public Map<String, String> getAllSectorsforUI() {
    Map<String, String> result = new LinkedHashMap<>();
    List<Sector> sectors = sectorRepository.findAll();
    for (Sector sector : sectors) {
      result.put(sector.getCode(), sector.getName());
    }
    return result;
  }

  @Transactional
  public TransactionResponse orgSignup(OrgSignupDTO orgSignupDTO, ApplicationContext context) {
    try {
      log.debug("Signing up Org=" + orgSignupDTO);
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
      context.setOrganization(organization);
      context.setLocale(Locale.US);
      context.setLoggedInUser(user.getUserId());

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
          userRoles.setOrgId(organization);
          genericService.saveEntity(userRoles, context);
        }
      }
      log.debug(
          "Org Reg Successfull"
              + organization.getOrgCode()
              + " :"
              + organization.getId()
              + " :"
              + organization.getOrganizationName());
    } catch (Exception e) {
      log.error("Error while org signup", e);
      Map<String, Object> mp = new HashMap<>();
      List<MomentusError> errors = new ArrayList<>();
      MomentusError momentusError =
          new MomentusError(
              GeneralMessages.UNIDIENTIFABLE_ERROR,
              generalMessages.getMessage(
                  GeneralMessages.UNIDIENTIFABLE_ERROR, context.getLocale()));
      errors.add(momentusError);
      TransactionResponse tr = new TransactionResponse(TransactionResponse.RESPONSE_STATUS.FAILURE);
      tr.setMomentusErrorList(errors);
      return tr;
    }
    TransactionResponse tr = new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
    tr.setResponseMesage(
        generalMessages.getMessage(GeneralMessages.ORG_CREATED_SUCCESS, context.getLocale()));
    return tr;
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
