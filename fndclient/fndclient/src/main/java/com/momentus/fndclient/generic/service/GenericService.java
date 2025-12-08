package com.momentus.fndclient.generic.service;

import com.momentus.fndclient.generic.dao.GenericDAO;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GenericService {

    @Autowired
    GenericDAO genericDAO;

    @Transactional
    public void createEntity(OrgBasedEntity entity, ApplicationContext context)
    {
        entity.setOrgId(context.getOrganization());
        entity.setCreatedBy(context.getLoggedInUser());
        entity.setCreatedTime(LocalDateTime.now());

        genericDAO.create(entity);

    }
}
