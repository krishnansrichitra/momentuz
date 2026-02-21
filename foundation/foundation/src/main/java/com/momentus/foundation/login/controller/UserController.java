package com.momentus.foundation.login.controller;

import com.momentus.foundation.accessgroup.dto.UserDTO;
import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.login.dto.PasswordUpdateDTO;
import com.momentus.foundation.login.service.AppUserDetailsService;
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
@RequestMapping("/api/user")
public class UserController {

  @Autowired ApplicationContextHelper applicationContextHelper;

  @Autowired AppUserDetailsService appUserDetailsService;

  @Autowired GeneralMessages generalMessages;

  private static final Logger log = LoggerFactory.getLogger(UserController.class);

  @GetMapping("/getByUserId")
  public ResponseEntity<UserDTO> getUserById(
      @RequestParam String userId, Authentication authentication) {

    ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
    try {
      UserDTO entity = appUserDetailsService.getUserFromId(userId, context);
      log.info("retreiving User ");
      return ResponseEntity.ok(entity);
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
      throw new RuntimeException();
      // return ResponseEntity.badRequest().body(null);
    }
  }

  @PostMapping("/create")
  public ResponseEntity<Map<String, Object>> createEntity(
      @RequestBody Map<String, Object> entityMap, Authentication authentication) {

    ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
    try {

      User entity = new User();
      TransactionResponse response = appUserDetailsService.createUser(entityMap, context);
      log.info("saving object");
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

  @PostMapping("/updatePassword")
  public ResponseEntity<Map<String, Object>> updatePassword(
      @RequestBody PasswordUpdateDTO passwordUpdateDTO, Authentication authentication) {

    ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
    log.debug("Calling updatePassword by " + context.getLoggedInUser());
    try {

      TransactionResponse response =
          appUserDetailsService.updatePassword(
              context.getLoggedInUser(),
              passwordUpdateDTO.getCurrentPassword(),
              passwordUpdateDTO.getNewPassword(),
              passwordUpdateDTO.getConfirmPassword(),
              context);
      log.info("Updating Password");
      return response.getResponseStatus().compareTo(TransactionResponse.RESPONSE_STATUS.FAILURE)
              == 0
          ? ResponseEntity.badRequest().body(response.errorMap())
          : ResponseEntity.ok(response.convertToMap());
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
}
