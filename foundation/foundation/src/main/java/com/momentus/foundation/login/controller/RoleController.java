package com.momentus.foundation.login.controller;

import com.momentus.foundation.accessgroup.model.Role;
import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.generic.service.GenericService;
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
@RequestMapping("/api/role")
public class RoleController {
  @Autowired ApplicationContextHelper applicationContextHelper;

  @Autowired GenericService genericService;

  @Autowired GeneralMessages generalMessages;

  private static final Logger log = LoggerFactory.getLogger(RoleController.class);

  @PostMapping("/create")
  public ResponseEntity<Map<String, Object>> createEntity(
      @RequestBody Map<String, Object> entityMap, Authentication authentication) {

    ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
    try {
      Role entity = new Role();
      TransactionResponse reponse = genericService.createOrUpdateEntity(entityMap, entity, context);
      if (reponse.hasHardError()) {
        return ResponseEntity.ok(reponse.convertToMap());
      } else {
        reponse.setResponseMesage(
            generalMessages.getMessage(
                GeneralMessages.ROLE_SAVED_SUCCESSFULLY, context.getLocale()));
        return ResponseEntity.ok(reponse.convertToMap());
      }
    } catch (Exception ex) {
      log.error("Error while creating Role", ex);
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
}
