package com.momentus.foundation.organization.service;

import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.organization.model.Industry;
import com.momentus.foundation.organization.model.Organization;
import com.momentus.foundation.organization.model.Sector;
import com.momentus.foundation.organization.repository.IndustryRepository;
import com.momentus.foundation.organization.repository.OrganizationRepository;
import com.momentus.foundation.organization.repository.SectorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class OrganizationService {

  @Autowired OrganizationRepository organizationRepository;

  @Autowired IndustryRepository industryRepository;

  @Autowired SectorRepository sectorRepository;

  @Autowired GeneralMessages generalMessages;

  @Cacheable
  public Organization getOrgById(Long id) {
    return (Organization) organizationRepository.findById(id).get();
  }

  @Cacheable
  public Organization getOrgByOrgCode(String code) {
    return (Organization) organizationRepository.findByOrgCode(code);
  }

  public TransactionResponse basicValidation(
      Organization organization, ApplicationContext context) {
    TransactionResponse transactionResponse = new TransactionResponse();
    List<MomentusError> momentusErrorList = new ArrayList<>();
    /* if (organization.getIndustry() == null) {
      momentusErrorList.add(
          new MomentusError(
              GeneralMessages.ORG_INDUSTRY_MANDATORY,
              generalMessages.getMessage(GeneralMessages.ORG_INDUSTRY_MANDATORY, Locale.US)));
    }*/

    if (!StringUtils.hasLength(organization.getOrgCode())) {
      momentusErrorList.add(
          new MomentusError(
              GeneralMessages.ORG_CODE_MANDATORY,
              generalMessages.getMessage(GeneralMessages.ORG_CODE_MANDATORY, Locale.US)));
    }
    Organization existingOrg = getOrgByOrgCode(organization.getOrgCode());
    if (existingOrg != null && existingOrg.getId() != organization.getId()) {
      momentusErrorList.add(
          new MomentusError(
              GeneralMessages.ORG_CODE_EXISTS,
              generalMessages.getMessage(GeneralMessages.ORG_CODE_EXISTS, Locale.US)));
    }

    if (organization.getSector() == null) {
      momentusErrorList.add(
          new MomentusError(
              GeneralMessages.ORG_SECTOR_MANDATORY,
              generalMessages.getMessage(GeneralMessages.ORG_SECTOR_MANDATORY, Locale.US)));
    }
    if (!CollectionUtils.isEmpty(momentusErrorList)) {
      transactionResponse.setMomentusErrorList(momentusErrorList);
      transactionResponse.setResponseStatus(TransactionResponse.RESPONSE_STATUS.FAILURE);
    }
    return transactionResponse;
  }

  public TransactionResponse saveOrganization(
      Organization organization, ApplicationContext context) {

    TransactionResponse basicValidationResponse = basicValidation(organization, context);
    if (basicValidationResponse.hasHardError()) {
      return basicValidationResponse;
    }
    if (organization.getIndustry() != null) {
      Industry industry =
          industryRepository.findById(organization.getIndustry().getCode()).orElse(null);
      organization.setIndustry(industry);
    }
    Sector sector = sectorRepository.findById(organization.getSector().getCode()).orElse(null);

    organization.setSector(sector);

    organizationRepository.save(organization);
    return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
  }
}
