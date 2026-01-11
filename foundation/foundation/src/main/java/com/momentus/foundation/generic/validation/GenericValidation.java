package com.momentus.foundation.generic.validation;

import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.generic.dao.GenericDAO;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class GenericValidation {

  @Autowired GeneralMessages generalMessages;

  @Autowired GenericDAO genericDAO;

  protected List<MomentusError> mandatoryFieldsValidation(
      OrgBasedEntity entity, ApplicationContext context) {
    TransactionResponse transactionResponse = new TransactionResponse();
    List<MomentusError> momentusErrorList = new ArrayList<>();
    Map<String, Object> mandatoryFields = entity.getMandatoryFields();
    if (mandatoryFields != null && !CollectionUtils.isEmpty(mandatoryFields)) {
      for (Map.Entry<String, Object> entry : mandatoryFields.entrySet()) {
        if (entry.getValue() == null) {
          String key = generalMessages.getMessage(entry.getKey(), context.getLocale());
          momentusErrorList.add(
              new MomentusError(
                  GeneralMessages.KEY_FIELD_MANDATORY,
                  generalMessages.getMessage(
                      GeneralMessages.KEY_FIELD_MANDATORY,
                      new Object[] {key},
                      context.getLocale())));
        }
      }
    }
    return momentusErrorList;
  }

  public TransactionResponse basicValidation(OrgBasedEntity entity, ApplicationContext context) {
    TransactionResponse transactionResponse = new TransactionResponse();
    List<MomentusError> momentusErrorList = new ArrayList<>();
    Map<String, Object> bkeys = entity.getBK();
    if (bkeys != null && !CollectionUtils.isEmpty(bkeys)) {
      for (Map.Entry<String, Object> entry : bkeys.entrySet()) {
        if (entry.getValue() == null) {
          String key = generalMessages.getMessage(entry.getKey(), context.getLocale());
          momentusErrorList.add(
              new MomentusError(
                  GeneralMessages.KEY_FIELD_MANDATORY,
                  generalMessages.getMessage(
                      GeneralMessages.KEY_FIELD_MANDATORY,
                      new Object[] {key},
                      context.getLocale())));
        } else if (String.class.isAssignableFrom(entry.getValue().getClass())) {
          String val = (String) entry.getValue();
          if (!StringUtils.hasLength(val)) {
            String key = generalMessages.getMessage(entry.getKey(), context.getLocale());
            momentusErrorList.add(
                new MomentusError(
                    GeneralMessages.KEY_FIELD_MANDATORY,
                    generalMessages.getMessage(
                        GeneralMessages.KEY_FIELD_MANDATORY,
                        new Object[] {key},
                        context.getLocale())));
          }
        }
      }
    }
    if (!CollectionUtils.isEmpty(momentusErrorList)) {
      transactionResponse.setMomentusErrorList(momentusErrorList);
      transactionResponse.setResponseStatus(TransactionResponse.RESPONSE_STATUS.FAILURE);
    } else {
      List<MomentusError> mandatoryFieldserrors = mandatoryFieldsValidation(entity, context);
      if (!CollectionUtils.isEmpty(mandatoryFieldserrors)) {
        transactionResponse.setMomentusErrorList(mandatoryFieldserrors);
        transactionResponse.setResponseStatus(TransactionResponse.RESPONSE_STATUS.FAILURE);
      }
    }
    return transactionResponse;
  }

  public OrgBasedEntity getByFilter(
      Map<String, Object> bk, Class<? extends OrgBasedEntity> cls, ApplicationContext context) {
    bk.put("orgId.id", context.getOrganization().getId());
    bk.put("deleted", false);
    OrgBasedEntity currentEntity = (OrgBasedEntity) genericDAO.loadByFilter(bk, cls);
    return currentEntity;
  }

  public TransactionResponse validateForDeletion(
      OrgBasedEntity orgBasedEntity, ApplicationContext context) {
    if (orgBasedEntity != null && orgBasedEntity.getId() != null && orgBasedEntity.getId() > 0) {
      orgBasedEntity =
          (OrgBasedEntity) genericDAO.loadById(orgBasedEntity.getClass(), orgBasedEntity.getId());
    } else if (!CollectionUtils.isEmpty(orgBasedEntity.getBK())) {
      orgBasedEntity =
          (OrgBasedEntity)
              genericDAO.loadByFilter(orgBasedEntity.getBK(), orgBasedEntity.getClass());
    }
    TransactionResponse transactionResponse = new TransactionResponse();
    List<MomentusError> momentusErrorList = new ArrayList<>();
    if (orgBasedEntity == null
        || orgBasedEntity.getOrgId().getId() != context.getOrganization().getId()) {
      momentusErrorList.add(
          new MomentusError(
              GeneralMessages.ENTITY_NOT_EXISTING,
              generalMessages.getMessage(
                  GeneralMessages.ENTITY_NOT_EXISTING, context.getLocale())));
    } else if (orgBasedEntity.isDeleted()) {
      momentusErrorList.add(
          new MomentusError(
              GeneralMessages.ENTITY_ALREADY_DELETED,
              generalMessages.getMessage(
                  GeneralMessages.ENTITY_ALREADY_DELETED, context.getLocale())));

    } else {
      transactionResponse.setTransactionEntity(orgBasedEntity);
    }
    if (!CollectionUtils.isEmpty(momentusErrorList)) {
      transactionResponse.setMomentusErrorList(momentusErrorList);
      transactionResponse.setResponseStatus(TransactionResponse.RESPONSE_STATUS.FAILURE);
    }

    return transactionResponse;
  }

  private List<MomentusError> getUniqueErrors(
      String objKey,
      Map<String, Object> keyFields,
      OrgBasedEntity orgBasedEntity,
      ApplicationContext context) {
    List<MomentusError> momentusErrorList = new ArrayList<>();
    OrgBasedEntity currentEntity =
        getByFilter(
            keyFields, (Class<? extends OrgBasedEntity>) orgBasedEntity.getClass(), context);
    if (currentEntity == null) return Arrays.asList();
    String key = generalMessages.getMessage(objKey, context.getLocale());
    if (orgBasedEntity.getPK() == null
        && currentEntity.getPK()
            != null) { // Adding new record checking if another record possess the ky
      momentusErrorList.add(
          new MomentusError(
              GeneralMessages.KEY_NOT_UNIQUE,
              generalMessages.getMessage(
                  GeneralMessages.KEY_NOT_UNIQUE, new Object[] {key}, context.getLocale())));
    }
    if (orgBasedEntity.getPK() != null
        && currentEntity.getPK()
            != orgBasedEntity
                .getPK()) { // updating existing record checking if another record possess the ky
      momentusErrorList.add(
          new MomentusError(
              GeneralMessages.KEY_NOT_UNIQUE,
              generalMessages.getMessage(
                  GeneralMessages.KEY_NOT_UNIQUE, new Object[] {key}, context.getLocale())));
    }
    return momentusErrorList;
  }

  public TransactionResponse bkUniqnessValidation(
      OrgBasedEntity orgBasedEntity, ApplicationContext context) {
    Map<String, Object> bk = orgBasedEntity.getBK();
    TransactionResponse transactionResponse = new TransactionResponse();
    List<MomentusError> momentusErrorList = new ArrayList<>();
    if (bk != null && !CollectionUtils.isEmpty(bk)) {
      momentusErrorList = getUniqueErrors(orgBasedEntity.getBKField(), bk, orgBasedEntity, context);
    }
    if (!CollectionUtils.isEmpty(momentusErrorList)) {
      transactionResponse.setMomentusErrorList(momentusErrorList);
      transactionResponse.setResponseStatus(TransactionResponse.RESPONSE_STATUS.FAILURE);
    }
    return transactionResponse;
  }

  public TransactionResponse nonBkUniqnessValidation(
      OrgBasedEntity orgBasedEntity, ApplicationContext context) {
    Map<String, Object> uniqueFields = orgBasedEntity.geUniqueFields();
    TransactionResponse transactionResponse = new TransactionResponse();
    List<MomentusError> momentusErrorList = new ArrayList<>();
    if (uniqueFields != null && !CollectionUtils.isEmpty(uniqueFields)) {
      for (Map.Entry<String, Object> entry : uniqueFields.entrySet()) {
        Map<String, Object> subObject = new HashMap<>();
        subObject.put(entry.getKey(), entry.getValue());
        List<MomentusError> subErrors =
            getUniqueErrors(entry.getKey(), subObject, orgBasedEntity, context);
        if (!CollectionUtils.isEmpty(subErrors)) {
          momentusErrorList.add(subErrors.get(0));
        }
      }
    }
    if (!CollectionUtils.isEmpty(momentusErrorList)) {
      transactionResponse.setMomentusErrorList(momentusErrorList);
      transactionResponse.setResponseStatus(TransactionResponse.RESPONSE_STATUS.FAILURE);
    }
    return transactionResponse;
  }

  private void populateBKInOrgBasedEntity(
      Map<String, Object> inputMap, OrgBasedEntity orgBasedEntity) {
    orgBasedEntity.setBK(inputMap.get(orgBasedEntity.getBKField()));
  }

  private void populateUniqueFieldsInOrgBasedEntity(
      Map<String, Object> inputMap, OrgBasedEntity orgBasedEntity) {
    orgBasedEntity.setUniqueFieldsFromMap(inputMap);
  }

  public TransactionResponse validateForUpdate(
      OrgBasedEntity orgBasedEntity, Map<String, Object> entityMap, ApplicationContext context) {
    TransactionResponse transactionResponse = new TransactionResponse();
    List<MomentusError> momentusErrorList = new ArrayList<>();

    if (entityMap != null && entityMap.get("version") == null) {
      momentusErrorList.add(
          new MomentusError(
              GeneralMessages.VERSION_NOT_PROVIDED,
              generalMessages.getMessage(
                  GeneralMessages.VERSION_NOT_PROVIDED, new Object[] {}, context.getLocale())));

    } else if (orgBasedEntity != null && orgBasedEntity.getId() != null) {
      OrgBasedEntity currentEntity =
          (OrgBasedEntity) genericDAO.loadById(orgBasedEntity.getClass(), orgBasedEntity.getId());
      populateBKInOrgBasedEntity(entityMap, orgBasedEntity);
      if (currentEntity == null
          || currentEntity.getOrgId().getId() != context.getOrganization().getId()) {
        momentusErrorList.add(
            new MomentusError(
                GeneralMessages.ENTITY_NOT_EXISTING,
                generalMessages.getMessage(
                    GeneralMessages.ENTITY_NOT_EXISTING, context.getLocale())));
      } else if (currentEntity.isDeleted()) {

        momentusErrorList.add(
            new MomentusError(
                GeneralMessages.ENTITY_DELETED,
                generalMessages.getMessage(GeneralMessages.ENTITY_DELETED, context.getLocale())));
      } else if (currentEntity.getVersion() > ((Number) entityMap.get("version")).longValue()) {
        momentusErrorList.add(
            new MomentusError(
                GeneralMessages.STALE_UPDATE,
                generalMessages.getMessage(GeneralMessages.STALE_UPDATE, context.getLocale())));
      } else if (!currentEntity.getBK().equals(orgBasedEntity.getBK())) {
        TransactionResponse newResponse = bkUniqnessValidation(orgBasedEntity, context);
        populateBKInOrgBasedEntity(entityMap, orgBasedEntity);
        if (newResponse.hasHardError()) {
          String key = generalMessages.getMessage(orgBasedEntity.getBKField(), context.getLocale());
          momentusErrorList.add(
              new MomentusError(
                  GeneralMessages.KEY_NOT_UNIQUE,
                  generalMessages.getMessage(
                      GeneralMessages.KEY_NOT_UNIQUE, new Object[] {key}, context.getLocale())));
        } else {
          transactionResponse.setTransactionEntity(currentEntity);
        }
      } else {
        populateUniqueFieldsInOrgBasedEntity(entityMap, orgBasedEntity);
        TransactionResponse newResponse = nonBkUniqnessValidation(orgBasedEntity, context);
        if (newResponse.hasHardError()) {
          momentusErrorList = newResponse.getMomentusErrorList();
        } else {
          transactionResponse.setTransactionEntity(currentEntity);
        }
      }
    }

    if (!CollectionUtils.isEmpty(momentusErrorList)) {
      transactionResponse.setMomentusErrorList(momentusErrorList);
      transactionResponse.setResponseStatus(TransactionResponse.RESPONSE_STATUS.FAILURE);
    }
    return transactionResponse;
  }
}
