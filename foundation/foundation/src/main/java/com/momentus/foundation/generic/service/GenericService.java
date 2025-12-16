package com.momentus.foundation.generic.service;

import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.generic.dao.GenericDAO;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class GenericService {

    @Autowired
    GenericDAO genericDAO;

    @Autowired
    MapToEntityMapper mapToEntityMapper;

    @Transactional
    public void createEntity(Map<String,Object> dataMap, OrgBasedEntity entity, ApplicationContext context)
    {
        mapToEntityMapper.populateFromMap(dataMap,entity);
        entity.setOrgId(context.getOrganization());
        entity.setCreatedBy(context.getLoggedInUser());
        entity.setCreatedTime(LocalDateTime.now());

        genericDAO.create(entity);

    }



}
