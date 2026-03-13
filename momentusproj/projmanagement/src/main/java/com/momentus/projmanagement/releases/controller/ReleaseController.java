package com.momentus.projmanagement.releases.controller;

import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.generic.controller.GenericController;
import com.momentus.projmanagement.releases.dto.SprintGenerationDTO;
import com.momentus.projmanagement.releases.service.SprintService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/release"})
public class ReleaseController {

  @Autowired ApplicationContextHelper applicationContextHelper;

  @Autowired GeneralMessages generalMessages;

  @Autowired SprintService sprintService;

  private static final Logger log = LoggerFactory.getLogger(GenericController.class);

  @PostMapping({"/generateSprints"})
  public ResponseEntity<Map<String, Object>> createEntity(
      @RequestBody SprintGenerationDTO sprintGenerationDTO, Authentication authentication) {
    ApplicationContext context = this.applicationContextHelper.generateAppContext(authentication);

    try {

      TransactionResponse transactionResponse =
          sprintService.generateSprints(context, sprintGenerationDTO);
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
}
