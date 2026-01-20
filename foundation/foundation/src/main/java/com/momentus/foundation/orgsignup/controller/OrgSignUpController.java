package com.momentus.foundation.orgsignup.controller;

import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.orgsignup.dto.OrgSignupDTO;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orgsignup")
public class OrgSignUpController {

  @PostMapping("/register")
  public ResponseEntity<Map<String, Object>> register(@RequestBody OrgSignupDTO orgSignupDTO) {

    return ResponseEntity.ok(
        new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS).convertToMap());
  }
}
