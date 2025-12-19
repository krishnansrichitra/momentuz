package com.momentus.foundation.generic.service;

import com.momentus.foundation.common.ApplicationConstants;
import com.momentus.foundation.common.ErrorMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.generic.dao.GenericDAO;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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


    private TransactionResponse basicValidation(OrgBasedEntity entity , ApplicationContext context) {
        TransactionResponse transactionResponse  =  new TransactionResponse();
        List<MomentusError> momentusErrorList = new ArrayList<>();
        Map<String,Object >  bkeys =  entity.getBK() ;
        if (bkeys != null && !CollectionUtils.isEmpty(bkeys)) {
            for (Map.Entry<String,Object> entry : bkeys.entrySet()) {
                if (entry.getValue() == null  )
                {
                    String key = errorMessages.getMessage(entry.getKey(),context.getLocale());
                    momentusErrorList.add( new MomentusError(ErrorMessages.KEY_FIELD_MANDATORY,
                            errorMessages.getMessage(ErrorMessages.KEY_FIELD_MANDATORY,  new Object[]{key},
                                    context.getLocale())));
                } else if(String.class.isAssignableFrom(entry.getValue().getClass())){
                    String val = (String) entry.getValue();
                    if(!StringUtils.hasLength(val))
                    {
                        String key = errorMessages.getMessage(entry.getKey(),context.getLocale());
                        momentusErrorList.add( new MomentusError(ErrorMessages.KEY_FIELD_MANDATORY,
                                errorMessages.getMessage(ErrorMessages.KEY_FIELD_MANDATORY,  new Object[]{key},
                                        context.getLocale())));
                    }

                }
            }
        }
        if (!CollectionUtils.isEmpty(momentusErrorList)) {
            transactionResponse.setMomentusErrorList(momentusErrorList);
            transactionResponse.setResponseStatus(TransactionResponse.RESPONSE_STATUS.FAILURE);
        }
        return transactionResponse;
    }

    @Transactional
    public TransactionResponse createEntity(Map<String,Object> dataMap, OrgBasedEntity entity, ApplicationContext context)
    {
        if(context.getOrganization().getId() != ApplicationConstants.ROOT_COMPANY) {
            entity.setOrgId(context.getOrganization());
        }
        mapToEntityMapper.populateFromMap(dataMap,entity,context);
        TransactionResponse basicValidationResponse =  basicValidation(entity,context);
        if(basicValidationResponse.hasHardError())
        {
            return basicValidationResponse;
        }
        entity.setCreatedBy(context.getLoggedInUser());
        entity.setCreatedTime(LocalDateTime.now());

        genericDAO.create(entity);
        return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);

    }



}
