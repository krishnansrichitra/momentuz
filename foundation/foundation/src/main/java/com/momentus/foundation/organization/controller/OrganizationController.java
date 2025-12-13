package com.momentus.foundation.organization.controller;

import com.momentus.foundation.common.JsonRepHelper;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.login.dto.AuthRequest;
import com.momentus.foundation.login.dto.AuthResponse;
import com.momentus.foundation.organization.model.Organization;
import com.momentus.foundation.organization.service.OrganizationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/organization")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;


    @PostMapping("/create")
    public ResponseEntity<Map<String,Object>> create(@RequestBody Map<String,Object> request) {

        Organization organization  = JsonRepHelper.getEntityFromMap(request, Organization.class);
        TransactionResponse transactionResponse = organizationService.saveOrganization(organization);
        return ResponseEntity.ok(transactionResponse.convertToMap());

    }

    @GetMapping("/getBasicMap")
    public ResponseEntity<Map<String,Object>> getBasicMap() {

        Organization organization  =new Organization();
        return ResponseEntity.ok(JsonRepHelper.getMapRepresentation(organization));

    }
}
