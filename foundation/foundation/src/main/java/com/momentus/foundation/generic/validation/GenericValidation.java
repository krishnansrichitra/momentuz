package com.momentus.foundation.generic.validation;

import com.momentus.foundation.common.ErrorMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.generic.dao.GenericDAO;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GenericValidation {

    @Autowired
    ErrorMessages errorMessages;

    @Autowired
    GenericDAO genericDAO;

    public TransactionResponse basicValidation(OrgBasedEntity entity , ApplicationContext context) {
        TransactionResponse transactionResponse  =  new TransactionResponse();
        List<MomentusError> momentusErrorList = new ArrayList<>();
        Map<String,Object > bkeys =  entity.getBK() ;
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

    public OrgBasedEntity getByBusinessKey(Map<String,Object> bk, Class<? extends OrgBasedEntity> cls, ApplicationContext context)
    {
        bk.put("orgId.id",context.getOrganization().getId());
        bk.put("deleted",false);
        OrgBasedEntity currentEntity = (OrgBasedEntity)genericDAO.loadByBK(bk,cls);
        return  currentEntity;
    }


    public TransactionResponse bkUniqnessValidation(OrgBasedEntity orgBasedEntity,ApplicationContext context) {
        Map<String,Object> bk = orgBasedEntity.getBK();
        OrgBasedEntity currentEntity = getByBusinessKey(bk, (Class<? extends OrgBasedEntity>)  orgBasedEntity.getClass() ,context);
        if (currentEntity == null)
            return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
        TransactionResponse transactionResponse = new TransactionResponse();
        List<MomentusError> momentusErrorList = new ArrayList<>();
        String key = errorMessages.getMessage(orgBasedEntity.getBKField(),context.getLocale());
        if (orgBasedEntity.getPK() == null && currentEntity.getPK() != null) {
            momentusErrorList.add(new MomentusError(ErrorMessages.KEY_NOT_UNIQUE,
                    errorMessages.getMessage(ErrorMessages.KEY_NOT_UNIQUE, new Object[]{key},
                            context.getLocale())));
        }
        if (orgBasedEntity.getPK() != null && currentEntity.getPK() != orgBasedEntity.getPK() ) {
            momentusErrorList.add(new MomentusError(ErrorMessages.KEY_NOT_UNIQUE,
                    errorMessages.getMessage(ErrorMessages.KEY_NOT_UNIQUE, new Object[]{key},
                            context.getLocale())));
        }
        if (!CollectionUtils.isEmpty(momentusErrorList)) {
            transactionResponse.setMomentusErrorList(momentusErrorList);
            transactionResponse.setResponseStatus(TransactionResponse.RESPONSE_STATUS.FAILURE);
        }
        return transactionResponse ;
    }



}
