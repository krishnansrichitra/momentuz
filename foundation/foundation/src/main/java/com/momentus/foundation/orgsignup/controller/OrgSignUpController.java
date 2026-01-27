package com.momentus.foundation.orgsignup.controller;

import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.orgsignup.dto.OrgSignupDTO;
import java.util.Map;

import com.momentus.foundation.orgsignup.service.OrgSignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orgsignup")
public class OrgSignUpController {

    @Autowired
   OrgSignupService orgSignupService;

   @Autowired
   ApplicationContextHelper applicationContextHelper;

  @PostMapping("/register")
  public ResponseEntity<Map<String, Object>> register(@RequestBody OrgSignupDTO orgSignupDTO) {
      ApplicationContext context = applicationContextHelper.generateRootContext();
      TransactionResponse response = orgSignupService.orgSignup(orgSignupDTO,context);
    return ResponseEntity.ok(
            response.convertToMap());
  }
}
