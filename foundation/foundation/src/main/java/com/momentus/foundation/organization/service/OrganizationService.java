package com.momentus.foundation.organization.service;

import com.momentus.foundation.organization.model.Organization;
import com.momentus.foundation.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

    @Autowired
    OrganizationRepository organizationRepository;


    @Cacheable
    public Organization getOrgById(Long id)
    {
       return (Organization) organizationRepository.findById(id).get();
    }
}
