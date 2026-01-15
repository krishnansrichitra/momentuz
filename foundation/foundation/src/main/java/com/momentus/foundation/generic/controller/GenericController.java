package com.momentus.foundation.generic.controller;

import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.JsonRepHelper;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.entity.service.EntityService;
import com.momentus.foundation.generic.service.GenericService;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/generic")
public class GenericController {
  @Autowired GenericService genericService;

  @Autowired ApplicationContextHelper applicationContextHelper;

  @Autowired EntityService entityService;

  @Autowired GeneralMessages generalMessages;

  private static final Logger log = LoggerFactory.getLogger(GenericController.class);

  //  @PreAuthorize("hasAuthority('custwr') or hasAuthority('adm')")
  @PostMapping("/createOrUpdate")
  public ResponseEntity<Map<String, Object>> createEntity(
      @RequestBody Map<String, Object> entityMap,
      @RequestParam String entityType,
      Authentication authentication) {
    ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
    try {
      String fullPackage = entityService.getFullPackage(entityType);

      // OrgBasedEntity entity = JsonRepHelper.getEntityFromMap(entityMap, (Class<? extends
      // OrgBasedEntity>) Class.forName(fullPackage));
      OrgBasedEntity entity = (OrgBasedEntity) Class.forName(fullPackage).newInstance();
      TransactionResponse transactionResponse =
          genericService.createOrUpdateEntity(entityMap, entity, context);
      if (transactionResponse
              .getResponseStatus()
              .compareTo(TransactionResponse.RESPONSE_STATUS.FAILURE)
          == 0) {
        return ResponseEntity.badRequest().body(transactionResponse.errorMap());
      }
      return ResponseEntity.ok(transactionResponse.convertToMap());
    } catch (Exception ex) {
      String error = ex.getMessage();
      Map<String, Object> mp = new HashMap<>();
      List<MomentusError> errors = new ArrayList<>();
      MomentusError momentusError =
          new MomentusError(
              GeneralMessages.UNIDIENTIFABLE_ERROR,
              generalMessages.getMessage(
                  GeneralMessages.UNIDIENTIFABLE_ERROR, context.getLocale()));
      errors.add(momentusError);
      mp.put("errors", errors);
      return ResponseEntity.badRequest().body(mp);
    }
  }

  @PostMapping("/bulkDelete")
  public ResponseEntity<Map<String, Object>> bulkDelete(
      @RequestBody List<Long> entityIdList,
      @RequestParam String entityType,
      Authentication authentication) {
    ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
    try {
      TransactionResponse response =
          genericService.deleteBulkEntity(entityIdList, entityType, context);
      return ResponseEntity.ok(response.convertToMap());

    } catch (Exception ex) {
      Map<String, Object> mp = new HashMap<>();
      List<MomentusError> errors = new ArrayList<>();
      MomentusError momentusError =
          new MomentusError(
              GeneralMessages.UNIDIENTIFABLE_ERROR,
              generalMessages.getMessage(
                  GeneralMessages.UNIDIENTIFABLE_ERROR, context.getLocale()));
      errors.add(momentusError);
      mp.put("errors", errors);
      return ResponseEntity.badRequest().body(mp);
    }
  }

  @PostMapping("/delete")
  public ResponseEntity<Map<String, Object>> deleteEntity(
      @RequestBody Map<String, Object> entityMap,
      @RequestParam String entityType,
      Authentication authentication) {

    try {
      String fullPackage = entityService.getFullPackage(entityType);
      ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
      OrgBasedEntity entity = (OrgBasedEntity) Class.forName(fullPackage).newInstance();
      TransactionResponse transactionResponse =
          genericService.deleteEntity(entityMap, entity, context);
      if (transactionResponse
              .getResponseStatus()
              .compareTo(TransactionResponse.RESPONSE_STATUS.FAILURE)
          == 0) {
        return ResponseEntity.badRequest().body(transactionResponse.errorMap());
      }
      return ResponseEntity.ok(transactionResponse.convertToMap());
    } catch (Exception ex) {
      String error = ex.getMessage();
      Map<String, Object> mp = new HashMap<>();
      mp.put("error", error);
      return ResponseEntity.badRequest().body(mp);
    }
  }

  @PostMapping("/getByBusinessKey")
  public ResponseEntity<Object> getByBusinessKey(
      @RequestBody Map<String, Object> entityMap,
      @RequestParam String entityType,
      Authentication authentication) {

    try {
      String fullPackage = entityService.getFullPackage(entityType);
      ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
      OrgBasedEntity entity =
          genericService.findByBusinessKey(
              entityMap, (Class<? extends OrgBasedEntity>) Class.forName(fullPackage), context);
      return ResponseEntity.ok(entity);
    } catch (Exception ex) {
      String error = ex.getMessage();
      Map<String, Object> mp = new HashMap<>();
      mp.put("error", error);
      return ResponseEntity.badRequest().body(mp);
    }
  }

  @PostMapping("/getRecordCount")
  public ResponseEntity<Map<String, Object>> getRecordCount(
      @RequestBody Map<String, Object> entityMap,
      @RequestParam String entityType,
      Authentication authentication) {

    try {
      String fullPackage = entityService.getFullPackage(entityType);
      ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
      long recordCount =
          genericService.getRecordCount(
              entityMap, (Class<? extends OrgBasedEntity>) Class.forName(fullPackage), context);
      Map<String, Long> result = new HashMap<>();
      result.put("count", recordCount);
      return ResponseEntity.ok(JsonRepHelper.getEntityToMap(result));
    } catch (Exception ex) {
      String error = ex.getMessage();
      Map<String, Object> mp = new HashMap<>();
      mp.put("error", error);
      return ResponseEntity.badRequest().body(mp);
    }
  }

  @PostMapping("/listRecords")
  public ResponseEntity<List<Object>> listRecords(
      @RequestBody Map<String, Object> entityMap,
      @RequestParam String entityType,
      @RequestParam Integer offset,
      @RequestParam Integer limit,
      Authentication authentication) {

    try {
      String fullPackage = entityService.getFullPackage(entityType);
      ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
      List<? extends OrgBasedEntity> orgBasedEntityList =
          genericService.listRecords(
              entityMap,
              (Class<? extends OrgBasedEntity>) Class.forName(fullPackage),
              context,
              offset,
              limit);
      return ResponseEntity.ok((List) orgBasedEntityList);
    } catch (Exception ex) {
      String error = ex.getMessage();
      List es = Arrays.asList(ex.getStackTrace());
      return ResponseEntity.badRequest().body(es);
    }
  }

  @PostMapping("/downloadCSV")
  public ResponseEntity<byte[]> downloadCSV(
      @RequestBody Map<String, Object> entityMap,
      @RequestParam String entityType,
      Authentication authentication) {

    try {
      String fullPackage = entityService.getFullPackage(entityType);
      ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
      byte[] csvData =
          genericService.downLoadRecords(
              entityMap, (Class<? extends OrgBasedEntity>) Class.forName(fullPackage), context);
      return ResponseEntity.ok(csvData);
    } catch (Exception ex) {
      String error = ex.getMessage();
      List es = Arrays.asList(ex.getStackTrace());
      return ResponseEntity.badRequest().body(null);
    }
  }

  @GetMapping("/getById")
  public ResponseEntity<Object> getEntityById(
      @RequestParam String entityType, @RequestParam Long id, Authentication authentication) {

    try {
      String fullPackage = entityService.getFullPackage(entityType);
      ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
      OrgBasedEntity entity =
          genericService.findById(
              id, (Class<? extends OrgBasedEntity>) Class.forName(fullPackage), context);
      return ResponseEntity.ok(entity);
    } catch (Exception ex) {
      String error = ex.getMessage();
      Map<String, Object> mp = new HashMap<>();
      mp.put("error", error);
      return ResponseEntity.badRequest().body(mp);
    }
  }

  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> uploadCsv(
      @RequestParam("file") MultipartFile file,
      @RequestParam String entityType,
      Authentication authentication) {

    if (file.isEmpty()) {
      return ResponseEntity.badRequest().body("Uploaded file is empty");
    }

    if (!file.getOriginalFilename().endsWith(".csv")) {
      return ResponseEntity.badRequest().body("Only CSV files are allowed");
    }

    ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
    genericService.uploaCSV(file, entityType, context);

    return ResponseEntity.ok("CSV uploaded and processed successfully");
  }
}
