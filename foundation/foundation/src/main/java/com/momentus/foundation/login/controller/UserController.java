package com.momentus.foundation.login.controller;

import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import com.momentus.foundation.login.service.AppUserDetailsService;
import java.util.HashMap;
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

  private static final Logger log = LoggerFactory.getLogger(UserController.class);

  @PostMapping("/create")
  public ResponseEntity<Map<String, String>> createEntity(
      @RequestBody Map<String, Object> entityMap, Authentication authentication) {

    try {

      ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
      // OrgBasedEntity entity = JsonRepHelper.getEntityFromMap(entityMap, (Class<? extends
      // OrgBasedEntity>) Class.forName(fullPackage));
      User entity = new User();
      appUserDetailsService.createUser(entityMap, context);
      Map<String, String> response = new HashMap<>();
      response.put("status", "success");
      response.put("message", "User created successfully");
      log.info("saving object");
      return ResponseEntity.ok(response);
    } catch (Exception ex) {
      String error = ex.getMessage();
      Map<String, String> mp = new HashMap<>();
      mp.put("error", error);
      return ResponseEntity.badRequest().body(mp);
    }
  }
}
