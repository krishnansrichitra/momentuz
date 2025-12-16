package com.momentus.foundation.organization.service;

import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.organization.model.Industry;
import com.momentus.foundation.organization.model.Organization;
import com.momentus.foundation.organization.model.Sector;
import com.momentus.foundation.organization.repository.IndustryRepository;
import com.momentus.foundation.organization.repository.OrganizationRepository;
import com.momentus.foundation.organization.repository.SectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;


    @Autowired
    IndustryRepository industryRepository;

    @Autowired
    SectorRepository sectorRepository;


    @Cacheable
    public Organization getOrgById(Long id)
    {
       return (Organization) organizationRepository.findById(id).get();
    }


    public TransactionResponse saveOrganization(Organization organization)
    {

        Industry industry = industryRepository.findById(organization.getIndustry().getCode()).orElse(null);
        Sector sector =sectorRepository.findById(organization.getSector().getCode()).orElse(null);
        organization.setIndustry(industry);
        organization.setSector(sector);

        organizationRepository.save(organization);
        return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);

    }
}
