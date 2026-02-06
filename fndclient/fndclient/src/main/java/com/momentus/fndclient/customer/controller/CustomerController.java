package com.momentus.fndclient.customer.controller;

import com.momentus.fndclient.customer.model.Customer;
import com.momentus.fndclient.customer.service.CustomerService;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.generic.service.GenericService;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

  private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

  @Autowired GenericService genericService;

  @Autowired ApplicationContextHelper applicationContextHelper;

  @Autowired CustomerService customerService;

  @PreAuthorize("hasAuthority('custwr') or hasAuthority('adm')")
  @PostMapping("/create")
  public ResponseEntity<Map<String, Object>> createCustomer(
      @RequestBody Map<String, Object> customer, Authentication authentication) {
    ApplicationContext context = applicationContextHelper.generateAppContext(authentication);

    Map<String, String> response = new HashMap<>();

    response.put("status", "success");
    response.put("message", "Customer created successfully");
    TransactionResponse transactionResponse =
        customerService.createEntity(customer, new Customer(), context);
    if (transactionResponse
            .getResponseStatus()
            .compareTo(TransactionResponse.RESPONSE_STATUS.FAILURE)
        == 0) {
      return ResponseEntity.badRequest().body(transactionResponse.errorMap());
    }
    return ResponseEntity.ok(transactionResponse.convertToMap());
  }
}
