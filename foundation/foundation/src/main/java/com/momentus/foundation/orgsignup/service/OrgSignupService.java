package com.momentus.foundation.orgsignup.service;

import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.organization.model.Organization;
import com.momentus.foundation.organization.model.Sector;
import com.momentus.foundation.organization.service.OrganizationService;
import com.momentus.foundation.orgsignup.dto.OrgSignupDTO;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrgSignupService {

  @Autowired OrganizationService organizationService;

  public TransactionResponse orgSignup(OrgSignupDTO orgSignupDTO) {
    Organization organization = createOrgFromDTO(orgSignupDTO);
    TransactionResponse transactionResponse =
        organizationService.saveOrganization(organization, null);
    if (transactionResponse.hasHardError()) {
      return transactionResponse;
    }
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
