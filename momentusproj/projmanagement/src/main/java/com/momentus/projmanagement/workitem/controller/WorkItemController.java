package com.momentus.projmanagement.workitem.controller;

import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.projmanagement.workitem.dto.WorkItemDTO;
import com.momentus.projmanagement.workitem.service.WorkItemService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/workitem"})
public class WorkItemController {

  @Autowired ApplicationContextHelper applicationContextHelper;

  @Autowired GeneralMessages generalMessages;

  @Autowired WorkItemService workItemService;

  private static final Logger log = LoggerFactory.getLogger(WorkItemController.class);

  @PostMapping({"/createOrUpdate"})
  public ResponseEntity<Map<String, Object>> createEntity(
      @RequestBody WorkItemDTO workItemDTO, Authentication authentication) {
    ApplicationContext context = this.applicationContextHelper.generateAppContext(authentication);

    try {

      TransactionResponse transactionResponse =
          workItemService.createOrUpdateEntity(workItemDTO, context);
      return transactionResponse
                  .getResponseStatus()
                  .compareTo(TransactionResponse.RESPONSE_STATUS.FAILURE)
              == 0
          ? ResponseEntity.badRequest().body(transactionResponse.errorMap())
          : ResponseEntity.ok(transactionResponse.convertToMap());
    } catch (Exception ex) {
      log.error("Error while creating obj", ex);
      String error = ex.getMessage();
      Map<String, Object> mp = new HashMap();
      List<MomentusError> errors = new ArrayList();
      MomentusError momentusError =
          new MomentusError("10000", this.generalMessages.getMessage("10000", context.getLocale()));
      errors.add(momentusError);
      mp.put("errors", errors);
      return ResponseEntity.badRequest().body(mp);
    }
  }

  @GetMapping({"/getById"})
  public ResponseEntity<WorkItemDTO> getEntityById(
      @RequestParam Long id, Authentication authentication) {
    try {
      ApplicationContext context = this.applicationContextHelper.generateAppContext(authentication);
      WorkItemDTO dto = workItemService.findById(id, context);
      return ResponseEntity.ok(dto);
    } catch (Exception ex) {
      String error = ex.getMessage();
      Map<String, Object> mp = new HashMap();
      mp.put("error", error);
      // return ResponseEntity.badRequest().body(new WorkItemDTO());
      return null;
    }
  }
}
