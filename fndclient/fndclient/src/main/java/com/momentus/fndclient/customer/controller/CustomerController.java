package com.momentus.fndclient.customer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {


    @PreAuthorize("hasAuthority('custwr') or hasAuthority('adm')")
    @PostMapping("/create")
    public ResponseEntity<Map<String,String>> createCustomer(@RequestBody Map<String,Object> customer)
    {

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Customer created successfully");

        return ResponseEntity.ok(response);

    }

}
