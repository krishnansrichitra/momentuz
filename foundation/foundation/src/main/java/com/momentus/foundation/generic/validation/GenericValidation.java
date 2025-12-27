package com.momentus.foundation.generic.validation;

import com.momentus.foundation.common.GeneralMessages;
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
    GeneralMessages generalMessages;

    @Autowired
    GenericDAO genericDAO;

    protected List<MomentusError> mandatoryFieldsValidation(OrgBasedEntity entity , ApplicationContext context) {
        TransactionResponse transactionResponse = new TransactionResponse();
        List<MomentusError> momentusErrorList = new ArrayList<>();
        Map<String,Object > mandatoryFields =  entity.getMandatoryFields() ;
        if (mandatoryFields != null && !CollectionUtils.isEmpty(mandatoryFields)) {
            for (Map.Entry<String, Object> entry : mandatoryFields.entrySet()) {
                if (entry.getValue() == null){
                    String key = generalMessages.getMessage(entry.getKey(),context.getLocale());
                    momentusErrorList.add( new MomentusError(GeneralMessages.KEY_FIELD_MANDATORY,
                            generalMessages.getMessage(GeneralMessages.KEY_FIELD_MANDATORY,  new Object[]{key},
                                    context.getLocale())));
                }
            }
        }
        return momentusErrorList;

    }


    public TransactionResponse basicValidation(OrgBasedEntity entity , ApplicationContext context) {
        TransactionResponse transactionResponse  =  new TransactionResponse();
        List<MomentusError> momentusErrorList = new ArrayList<>();
        Map<String,Object > bkeys =  entity.getBK() ;
        if (bkeys != null && !CollectionUtils.isEmpty(bkeys)) {
            for (Map.Entry<String,Object> entry : bkeys.entrySet()) {
                if (entry.getValue() == null  )
                {
                    String key = generalMessages.getMessage(entry.getKey(),context.getLocale());
                    momentusErrorList.add( new MomentusError(GeneralMessages.KEY_FIELD_MANDATORY,
                            generalMessages.getMessage(GeneralMessages.KEY_FIELD_MANDATORY,  new Object[]{key},
                                    context.getLocale())));
                } else if(String.class.isAssignableFrom(entry.getValue().getClass())){
                    String val = (String) entry.getValue();
                    if(!StringUtils.hasLength(val))
                    {
                        String key = generalMessages.getMessage(entry.getKey(),context.getLocale());
                        momentusErrorList.add( new MomentusError(GeneralMessages.KEY_FIELD_MANDATORY,
                                generalMessages.getMessage(GeneralMessages.KEY_FIELD_MANDATORY,  new Object[]{key},
                                        context.getLocale())));
                    }

                }
            }
        }
        if (!CollectionUtils.isEmpty(momentusErrorList)) {
            transactionResponse.setMomentusErrorList(momentusErrorList);
            transactionResponse.setResponseStatus(TransactionResponse.RESPONSE_STATUS.FAILURE);
        } else
        {
            List<MomentusError> mandatoryFieldserrors  =mandatoryFieldsValidation(entity,context);
            if (!CollectionUtils.isEmpty(mandatoryFieldserrors)) {
                transactionResponse.setMomentusErrorList(mandatoryFieldserrors);
                transactionResponse.setResponseStatus(TransactionResponse.RESPONSE_STATUS.FAILURE);
            }

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

   public TransactionResponse validateForDeletion(OrgBasedEntity orgBasedEntity,ApplicationContext context)
   {
       if(orgBasedEntity != null && orgBasedEntity.getId() != null && orgBasedEntity.getId() > 0) {
                orgBasedEntity  = (OrgBasedEntity)genericDAO.loadById(orgBasedEntity.getClass(),orgBasedEntity.getId());
       }else if (!CollectionUtils.isEmpty(orgBasedEntity.getBK())) {
             orgBasedEntity = (OrgBasedEntity) genericDAO.loadByBK(orgBasedEntity.getBK(), orgBasedEntity.getClass());
        }
       TransactionResponse transactionResponse = new TransactionResponse();
       List<MomentusError> momentusErrorList = new ArrayList<>();
        if (orgBasedEntity == null || orgBasedEntity.getOrgId().getId() != context.getOrganization().getId() ) {
            momentusErrorList.add( new MomentusError(GeneralMessages.ENTITY_NOT_EXISTING,
                    generalMessages.getMessage(GeneralMessages.ENTITY_NOT_EXISTING,
                            context.getLocale())));
        }else if (orgBasedEntity.isDeleted()) {
            momentusErrorList.add( new MomentusError(GeneralMessages.ENTITY_ALREADY_DELETED,
                    generalMessages.getMessage(GeneralMessages.ENTITY_ALREADY_DELETED,
                            context.getLocale())));

        }else {
            transactionResponse.setTransactionEntity(orgBasedEntity);
        }
       if (!CollectionUtils.isEmpty(momentusErrorList)) {
           transactionResponse.setMomentusErrorList(momentusErrorList);
           transactionResponse.setResponseStatus(TransactionResponse.RESPONSE_STATUS.FAILURE);
       }

       return transactionResponse ;



   }

    public TransactionResponse bkUniqnessValidation(OrgBasedEntity orgBasedEntity,ApplicationContext context) {
        Map<String,Object> bk = orgBasedEntity.getBK();
        TransactionResponse transactionResponse = new TransactionResponse();
        List<MomentusError> momentusErrorList = new ArrayList<>();
        if (bk != null && !CollectionUtils.isEmpty(bk)) {
            OrgBasedEntity currentEntity = getByBusinessKey(bk, (Class<? extends OrgBasedEntity>) orgBasedEntity.getClass(), context);
            if (currentEntity == null)
                return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
            String key = generalMessages.getMessage(orgBasedEntity.getBKField(), context.getLocale());
            if (orgBasedEntity.getPK() == null && currentEntity.getPK() != null) {
                momentusErrorList.add(new MomentusError(GeneralMessages.KEY_NOT_UNIQUE,
                        generalMessages.getMessage(GeneralMessages.KEY_NOT_UNIQUE, new Object[]{key},
                                context.getLocale())));
            }
            if (orgBasedEntity.getPK() != null && currentEntity.getPK() != orgBasedEntity.getPK()) {
                momentusErrorList.add(new MomentusError(GeneralMessages.KEY_NOT_UNIQUE,
                        generalMessages.getMessage(GeneralMessages.KEY_NOT_UNIQUE, new Object[]{key},
                                context.getLocale())));
            }
        }
        if (!CollectionUtils.isEmpty(momentusErrorList)) {
            transactionResponse.setMomentusErrorList(momentusErrorList);
            transactionResponse.setResponseStatus(TransactionResponse.RESPONSE_STATUS.FAILURE);
        }
        return transactionResponse ;
    }


    private void populateBKInOrgBasedEntity(Map<String,Object> inputMap ,OrgBasedEntity orgBasedEntity)
    {
        orgBasedEntity.setBK(inputMap.get(orgBasedEntity.getBKField()));

    }

    public TransactionResponse validateForUpdate(OrgBasedEntity orgBasedEntity, Map<String,Object> entityMap , ApplicationContext context)
    {
        TransactionResponse transactionResponse = new TransactionResponse();
        List<MomentusError> momentusErrorList = new ArrayList<>();

        if(entityMap != null && entityMap.get("version") == null )
        {
             momentusErrorList.add( new MomentusError(GeneralMessages.VERSION_NOT_PROVIDED,
                     generalMessages.getMessage(GeneralMessages.VERSION_NOT_PROVIDED, new Object[]{},
                             context.getLocale())));

        }else if (orgBasedEntity != null && orgBasedEntity.getId() != null ) {
            OrgBasedEntity currentEntity  = (OrgBasedEntity)genericDAO.loadById(orgBasedEntity.getClass(),orgBasedEntity.getId());
            populateBKInOrgBasedEntity(entityMap,orgBasedEntity);
            if(currentEntity == null || currentEntity.getOrgId().getId() != context.getOrganization().getId())
            {
                momentusErrorList.add( new MomentusError(GeneralMessages.ENTITY_NOT_EXISTING,
                        generalMessages.getMessage(GeneralMessages.ENTITY_NOT_EXISTING,
                                context.getLocale())));
            }else if (currentEntity.isDeleted()) {

                momentusErrorList.add( new MomentusError(GeneralMessages.ENTITY_DELETED,
                        generalMessages.getMessage(GeneralMessages.ENTITY_DELETED,
                                context.getLocale())));
            }else if (currentEntity.getVersion() > ((Number) entityMap.get("version")).longValue() )
            {
                momentusErrorList.add( new MomentusError(GeneralMessages.STALE_UPDATE,
                        generalMessages.getMessage(GeneralMessages.STALE_UPDATE,
                                context.getLocale())));
            }else if(!currentEntity.getBK().equals(orgBasedEntity.getBK())) {
                TransactionResponse newResponse = bkUniqnessValidation(orgBasedEntity,context);
                if(newResponse.hasHardError()){
                    String key = generalMessages.getMessage(orgBasedEntity.getBKField(),context.getLocale());
                    momentusErrorList.add(new MomentusError(GeneralMessages.KEY_NOT_UNIQUE,
                            generalMessages.getMessage(GeneralMessages.KEY_NOT_UNIQUE, new Object[]{key},
                                    context.getLocale())));
                }else {
                    transactionResponse.setTransactionEntity(currentEntity);
                }
            }else {
                transactionResponse.setTransactionEntity(currentEntity);
            }
        }

        if (!CollectionUtils.isEmpty(momentusErrorList)) {
            transactionResponse.setMomentusErrorList(momentusErrorList);
            transactionResponse.setResponseStatus(TransactionResponse.RESPONSE_STATUS.FAILURE);
        }
        return transactionResponse ;

    }

}
