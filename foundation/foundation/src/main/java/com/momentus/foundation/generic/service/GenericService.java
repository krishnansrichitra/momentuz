package com.momentus.foundation.generic.service;

import com.momentus.foundation.common.ApplicationConstants;
import com.momentus.foundation.common.ErrorMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.generic.dao.GenericDAO;
import com.momentus.foundation.generic.validation.GenericValidation;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class GenericService {

    @Autowired
    GenericDAO genericDAO;

    @Autowired
    MapToEntityMapper mapToEntityMapper;

    @Autowired
    ErrorMessages errorMessages;

    @Autowired
    GenericValidation genericValidation;




    private TransactionResponse validate(OrgBasedEntity entity, ApplicationContext context)
    {
        TransactionResponse validationResponse =  genericValidation.basicValidation(entity,context);
        if(validationResponse.hasHardError())
        {
            return validationResponse;
        }
        validationResponse = genericValidation.bkUniqnessValidation(entity,context);
        if(validationResponse.hasHardError())
        {
            return validationResponse;
        }
        return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);

    }



    @Transactional
    public TransactionResponse createEntity(Map<String,Object> dataMap, OrgBasedEntity entity, ApplicationContext context)
    {
        if(context.getOrganization().getId() != ApplicationConstants.ROOT_COMPANY) {
            entity.setOrgId(context.getOrganization());
        }
        mapToEntityMapper.populateFromMap(dataMap,entity,context);
        TransactionResponse validationResponse  = validate(entity,context);
        if(validationResponse.hasHardError())
        {
            return validationResponse;
        }
        return saveEntity(entity,context);

    }


    public TransactionResponse saveEntity(OrgBasedEntity entity, ApplicationContext context)
    {
        entity.setCreatedBy(context.getLoggedInUser());
        entity.setCreatedTime(LocalDateTime.now());
        entity.setLastUpdatedBy(context.getLoggedInUser());
        entity.setLastUpdatedTime(LocalDateTime.now());
        genericDAO.create(entity);
        return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);


    }





}
