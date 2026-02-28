package com.momentus.foundation.generic.service;

import com.momentus.foundation.common.ApplicationConstants;
import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.entity.service.EntityService;
import com.momentus.foundation.generic.dao.GenericDAO;
import com.momentus.foundation.generic.validation.GenericValidation;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import jakarta.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

@Service
public class GenericService {

  @Autowired GenericDAO genericDAO;

  @Autowired MapToEntityMapper mapToEntityMapper;

  @Autowired GeneralMessages generalMessages;

  @Autowired GenericValidation genericValidation;

  @Autowired EntityService entityService;

  public OrgBasedEntity findById(
      Object pk, Class<? extends OrgBasedEntity> cls, ApplicationContext context) {
    return findById(pk, cls, context, true);
  }

  public OrgBasedEntity findById(
      Object pk,
      Class<? extends OrgBasedEntity> cls,
      ApplicationContext context,
      boolean excludedDeleted) {

    OrgBasedEntity currentEntity = (OrgBasedEntity) genericDAO.loadById(cls, (Long) pk);
    if (currentEntity != null
        && currentEntity.getOrgId().getId() == context.getOrganization().getId()
        && (!currentEntity.isDeleted() || !excludedDeleted)) return currentEntity;
    else return null;
  }

  public OrgBasedEntity findByBusinessKey(
      Map<String, Object> bk, Class<? extends OrgBasedEntity> cls, ApplicationContext context) {
    return findByBusinessKey(bk, cls, context, true);
  }

  public OrgBasedEntity findByBusinessKey(
      Map<String, Object> bk,
      Class<? extends OrgBasedEntity> cls,
      ApplicationContext context,
      boolean excludedDeleted) {
    bk.put("orgId.id", context.getOrganization().getId());
    if (excludedDeleted) bk.put("deleted", false);
    OrgBasedEntity currentEntity = (OrgBasedEntity) genericDAO.loadByFilter(bk, cls);

    return currentEntity;
  }

  public long getRecordCount(
      Map<String, Object> filter, Class<? extends OrgBasedEntity> cls, ApplicationContext context) {
    return getRecordCount(filter, cls, context, false);
  }

  public long getRecordCount(
      Map<String, Object> filter,
      Class<? extends OrgBasedEntity> cls,
      ApplicationContext context,
      boolean excludedDeleted) {
    filter.put("orgId.id", context.getOrganization().getId());
    if (excludedDeleted) filter.put("deleted", false);
    return genericDAO.getCountForList(filter, cls);
  }

  public List<? extends OrgBasedEntity> listRecords(
      Map<String, Object> filter,
      Class<? extends OrgBasedEntity> cls,
      ApplicationContext context,
      int offset,
      int limit) {
    return listRecords(filter, cls, context, offset, limit, false);
  }

  private static void putWithPath(Map<String, Object> map, String keyPath, Object value) {

    if (keyPath == null || keyPath.isEmpty()) {
      return;
    }
    int dotIndex = keyPath.indexOf('.');
    // Base case: no dot
    if (dotIndex == -1) {
      map.put(keyPath, value);
      return;
    }
    // Recursive case
    String firstPart = keyPath.substring(0, dotIndex);
    String remainingPart = keyPath.substring(dotIndex + 1);
    Map<String, Object> nestedMap;
    if (map.containsKey(firstPart)) {
      nestedMap = (Map) map.get(firstPart);
    } else {
      nestedMap = new HashMap<>();
    }

    // recursive call
    putWithPath(nestedMap, remainingPart, value);
    // put nested map in caller
    map.put(firstPart, nestedMap);
  }

  @Transactional
  public void uploaCSV(MultipartFile file, String entityType, ApplicationContext context) {
    try (BufferedReader reader =
        new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

      String line;
      int row = 0;
      List<String> fields = new ArrayList<>();
      List<Map<String, Object>> records = new ArrayList<>();
      while ((line = reader.readLine()) != null) {
        row++;
        Map<String, Object> record = new LinkedHashMap<>();
        String[] columns = line.split(",");
        for (int i = 0; i < columns.length; i++) {
          String col = columns[i];
          if (row == 1) {
            fields.add(col);
          } else {
            putWithPath(record, fields.get(i), col);
          }
        }
        if (row > 1) {
          records.add(record);
          try {
            String fullPackage = entityService.getFullPackage(entityType);
            OrgBasedEntity entity = (OrgBasedEntity) Class.forName(fullPackage).newInstance();
            createOrUpdateEntity(record, entity, context);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public byte[] downLoadRecords(
      Map<String, Object> filter, Class<? extends OrgBasedEntity> cls, ApplicationContext context) {
    List<? extends OrgBasedEntity> orgBasedEntityList =
        listRecords(filter, cls, context, 0, 9999, true);
    List<Map<String, Object>> records = new ArrayList<>();
    orgBasedEntityList.stream()
        .forEach(
            orgBasedEntity -> {
              Map<String, Object> record =
                  mapToEntityMapper.converToMapFromEntity(orgBasedEntity, false);
              records.add(record);
            });
    StringBuilder csvBuilder = new StringBuilder();

    // Header
    Set<String> headers = records.get(0).keySet();
    csvBuilder.append(String.join(",", headers)).append("\n");
    for (Map<String, Object> row : records) {
      for (String header : headers) {
        Object value = row.get(header);
        csvBuilder.append(value != null ? value.toString() : "").append(",");
      }
      csvBuilder.deleteCharAt(csvBuilder.length() - 1); // remove last comma
      csvBuilder.append("\n");
    }

    byte[] csvBytes = csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
    return csvBytes;
  }

  public List<? extends OrgBasedEntity> listRecords(
      Map<String, Object> filter,
      Class<? extends OrgBasedEntity> cls,
      ApplicationContext context,
      int offset,
      int limit,
      boolean excludedDeleted) {
    filter.put("orgId.id", context.getOrganization().getId());
    if (excludedDeleted) filter.put("deleted", false);
    return genericDAO.listByFilter(filter, cls, offset, limit);
  }

  public List<String> listFields(
      Map<String, Object> filter,
      Class<? extends OrgBasedEntity> cls,
      ApplicationContext context,
      String field,
      int offset,
      int limit,
      boolean excludedDeleted) {
    filter.put("orgId.id", context.getOrganization().getId());
    if (excludedDeleted) filter.put("deleted", false);
    return genericDAO.listFieldByFilter(filter, cls, field, String.class, offset, limit);
  }

  protected TransactionResponse validate(
      OrgBasedEntity entity, ApplicationContext context, boolean skipBKValidation) {
    TransactionResponse validationResponse = genericValidation.basicValidation(entity, context);
    if (validationResponse.hasHardError()) {
      return validationResponse;
    }
    if (!skipBKValidation) {
      validationResponse = genericValidation.bkUniqnessValidation(entity, context);
    }
    if (validationResponse.hasHardError()) {
      return validationResponse;
    }
    if (!skipBKValidation) {
      validationResponse = genericValidation.nonBkUniqnessValidation(entity, context);
    }
    if (validationResponse.hasHardError()) {
      return validationResponse;
    }
    return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
  }

  @Transactional
  public TransactionResponse deleteEntity(
      Map<String, Object> dataMap, OrgBasedEntity entity, ApplicationContext context) {
    if (context.getOrganization().getId() != ApplicationConstants.ROOT_COMPANY) {
      entity.setOrgId(context.getOrganization());
    }
    if (dataMap != null
        && dataMap.containsKey("id")
        && ((Number) dataMap.get("id")).longValue() > 0) {
      entity.setId(((Number) dataMap.get("id")).longValue());
    } else {
      entity.setBK(dataMap.get(entity.getBKField()));
    }

    TransactionResponse response = genericValidation.validateForDeletion(entity, context);
    if (response.hasHardError()) {
      return response;
    } else {

      entity = (OrgBasedEntity) response.getTransactionEntity();
      entity.setDeleted(true);
      return saveEntity(entity, context);
    }
  }

  @Transactional
  public TransactionResponse deleteBulkEntity(
      List<Long> pks, String entityName, ApplicationContext context) {

    try {
      for (Long pk : pks) {
        String fullPackage = entityService.getFullPackage(entityName);
        OrgBasedEntity entity = (OrgBasedEntity) Class.forName(fullPackage).newInstance();
        if (context.getOrganization().getId() != ApplicationConstants.ROOT_COMPANY) {
          entity.setOrgId(context.getOrganization());
        }
        entity.setId(pk);
        TransactionResponse response = genericValidation.validateForDeletion(entity, context);
        if (response.hasHardError()) {
          //  return response;
        } else {

          entity = (OrgBasedEntity) response.getTransactionEntity();
          entity.setDeleted(true);
          saveEntity(entity, context);
        }
      }
      return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
    } catch (Exception ex) {
      TransactionResponse transactionResponse = new TransactionResponse();
      List<MomentusError> errors = new ArrayList<>();
      MomentusError momentusError =
          new MomentusError(
              GeneralMessages.UNIDIENTIFABLE_ERROR,
              generalMessages.getMessage(
                  GeneralMessages.UNIDIENTIFABLE_ERROR, context.getLocale()));
      errors.add(momentusError);
      transactionResponse.setMomentusErrorList(errors);
      return transactionResponse;
    }
  }

  @Transactional
  public TransactionResponse createOrUpdateEntity(
      Map<String, Object> dataMap, OrgBasedEntity entity, ApplicationContext context) {
    boolean skipBKValidation = false;

    if (context.getOrganization().getId() != ApplicationConstants.ROOT_COMPANY) {
      entity.setOrgId(context.getOrganization());
    }
    if (dataMap != null
        && dataMap.containsKey("id")
        && dataMap.get("id") != null
        && ((Number) dataMap.get("id")).longValue() > 0) {
      entity.setId(((Number) dataMap.get("id")).longValue());
      TransactionResponse validationResponse =
          genericValidation.validateForUpdate(entity, dataMap, context);
      if (validationResponse.hasHardError()) {
        return validationResponse;
      } else {
        entity = (OrgBasedEntity) validationResponse.getTransactionEntity();
        skipBKValidation = true;
      }
    }
    prepopulate(entity, context);
    mapToEntityMapper.populateFromMap(dataMap, entity, context);
    preValidation(entity, context);
    TransactionResponse validationResponse = validate(entity, context, skipBKValidation);
    if (validationResponse.hasHardError()) {
      TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
      return validationResponse;
    }
    preSave(entity, context);
    return saveEntity(entity, context);
  }

  protected void prepopulate(BaseEntity entity, ApplicationContext context) {}

  protected void preValidation(BaseEntity entity, ApplicationContext context) {}

  protected void preSave(BaseEntity entity, ApplicationContext context) {}

  public TransactionResponse saveEntity(OrgBasedEntity entity, ApplicationContext context) {
    entity.setCreatedBy(context.getLoggedInUser());
    entity.setCreatedTime(LocalDateTime.now());
    entity.setLastUpdatedBy(context.getLoggedInUser());
    entity.setLastUpdatedTime(LocalDateTime.now());
    genericDAO.create(entity);
    return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS, entity);
  }
}
