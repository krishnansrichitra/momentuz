package com.momentus.foundation.generic.service;

import com.momentus.foundation.common.ApplicationConstants;
import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.generic.dao.GenericDAO;
import com.momentus.foundation.generic.validation.GenericValidation;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class GenericService {

    @Autowired
    GenericDAO genericDAO;

    @Autowired
    MapToEntityMapper mapToEntityMapper;

    @Autowired
    GeneralMessages generalMessages;

    @Autowired
    GenericValidation genericValidation;


    public OrgBasedEntity findById(Object pk, Class<? extends OrgBasedEntity> cls, ApplicationContext context)
    {
        return findById(pk,cls,context,true);
    }

    public OrgBasedEntity findById(Object pk, Class<? extends OrgBasedEntity> cls, ApplicationContext context, boolean excludedDeleted)
    {

        OrgBasedEntity currentEntity = (OrgBasedEntity)genericDAO.loadById(cls,(Long) pk);
        if (currentEntity != null &&    currentEntity.getOrgId().getId() == context.getOrganization().getId() && (   !currentEntity.isDeleted()  || !excludedDeleted)  )
            return  currentEntity;
        else
            return null ;

    }

    public OrgBasedEntity findByBusinessKey(Map<String,Object> bk, Class<? extends OrgBasedEntity> cls, ApplicationContext context)
    {
       return findByBusinessKey(bk,cls,context,true);
    }

    public OrgBasedEntity findByBusinessKey(Map<String,Object> bk, Class<? extends OrgBasedEntity> cls, ApplicationContext context,boolean excludedDeleted)
    {
        bk.put("orgId.id",context.getOrganization().getId());
        if (excludedDeleted) bk.put("deleted",false);
        OrgBasedEntity currentEntity = (OrgBasedEntity)genericDAO.loadByBK(bk,cls);
        return  currentEntity;
    }


    public long getRecordCount(Map<String,Object> filter, Class<? extends OrgBasedEntity> cls, ApplicationContext context)
    {
        return getRecordCount(filter,cls,context,false);
    }

    public long getRecordCount(Map<String,Object> filter, Class<? extends OrgBasedEntity> cls, ApplicationContext context,boolean excludedDeleted)
    {
        filter.put("orgId.id",context.getOrganization().getId());
        if (excludedDeleted) filter.put("deleted",false);
        return genericDAO.getCountForList(filter,cls);

    }

    public List<? extends OrgBasedEntity> listRecords(Map<String,Object> filter, Class<? extends OrgBasedEntity> cls, ApplicationContext context,int offset, int limit)
    {
        return listRecords(filter,cls,context,offset,limit,false);
    }

    public List<? extends OrgBasedEntity> listRecords(Map<String,Object> filter, Class<? extends OrgBasedEntity> cls, ApplicationContext context, int offset, int limit, boolean excludedDeleted)
    {
        filter.put("orgId.id",context.getOrganization().getId());
        if (excludedDeleted) filter.put("deleted",false);
        return genericDAO.listByFilter(filter,cls,offset,limit);

    }

    protected TransactionResponse validate(OrgBasedEntity entity, ApplicationContext context,boolean skipBKValidation)
    {
        TransactionResponse validationResponse =  genericValidation.basicValidation(entity,context);
        if(validationResponse.hasHardError())
        {
            return validationResponse;
        }
        if (!skipBKValidation)
        validationResponse = genericValidation.bkUniqnessValidation(entity,context);
        if(validationResponse.hasHardError())
        {
            return validationResponse;
        }
        return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);

    }




    @Transactional
    public TransactionResponse deleteEntity(Map<String,Object> dataMap, OrgBasedEntity entity, ApplicationContext context)
    {
        if(context.getOrganization().getId() != ApplicationConstants.ROOT_COMPANY) {
            entity.setOrgId(context.getOrganization());
        }
        if (dataMap != null && dataMap.containsKey("id") && ((Number) dataMap.get("id")).longValue() > 0 ) {
            entity.setId(((Number) dataMap.get("id")).longValue());
        }else {
            entity.setBK(dataMap.get(entity.getBKField()));
        }

        TransactionResponse response = genericValidation.validateForDeletion(entity,context);
        if (response.hasHardError()) {
            return response ;
        }else{

            entity = (OrgBasedEntity) response.getTransactionEntity();
            entity.setDeleted(true);
            return saveEntity(entity,context);
        }


    }


    @Transactional
    public TransactionResponse createOrUpdateEntity(Map<String,Object> dataMap, OrgBasedEntity entity, ApplicationContext context)
    {
        boolean skipBKValidation = false;

        if(context.getOrganization().getId() != ApplicationConstants.ROOT_COMPANY) {
            entity.setOrgId(context.getOrganization());
        }
        if (dataMap != null && dataMap.containsKey("id") && ((Number) dataMap.get("id")).longValue() > 0 ) {
            entity.setId(((Number) dataMap.get("id")).longValue() );
            TransactionResponse validationResponse  =   genericValidation.validateForUpdate(entity,dataMap,context) ;
            if(validationResponse.hasHardError())
            {
                return validationResponse;
            }else {
                entity = (OrgBasedEntity) validationResponse.getTransactionEntity();
                skipBKValidation=true;
            }

        }
        mapToEntityMapper.populateFromMap(dataMap,entity,context);
        TransactionResponse validationResponse  = validate(entity,context,skipBKValidation);
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
        return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS,entity);

    }





}
