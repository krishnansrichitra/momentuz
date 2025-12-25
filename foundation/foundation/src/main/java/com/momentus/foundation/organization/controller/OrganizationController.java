package com.momentus.foundation.organization.controller;

import com.momentus.foundation.common.JsonRepHelper;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import com.momentus.foundation.organization.model.Organization;
import com.momentus.foundation.organization.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/organization")
public class OrganizationController {

    @Autowired
    OrganizationService organizationService;

    @Autowired
    ApplicationContextHelper applicationContextHelper;

    @PostMapping("/create")
    public ResponseEntity<Map<String,Object>> create(@RequestBody Map<String,Object> request, Authentication authentication) {
        ApplicationContext applicationContext = applicationContextHelper.generateAppContext(authentication);
        Organization organization  = JsonRepHelper.getEntityFromMap(request, Organization.class);
        TransactionResponse transactionResponse = organizationService.saveOrganization(organization,applicationContext);
       if(transactionResponse.getResponseStatus().compareTo(TransactionResponse.RESPONSE_STATUS.FAILURE) ==0 ) {
           return ResponseEntity.badRequest().body(transactionResponse.errorMap());
       }
        return ResponseEntity.ok(transactionResponse.convertToMap());

    }

    @PostMapping("/getByOrgCode")
    public ResponseEntity<Map<String,Object>> getByOrgCode( @RequestParam String orgCode, Authentication authentication )
    {

        try {
            ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
            Organization entity =   organizationService.getOrgByOrgCode(orgCode);
            return ResponseEntity.ok(JsonRepHelper.getEntityToMap(entity));
        }catch (Exception ex ){
            String error =  ex.getMessage();
            Map<String,Object> mp = new HashMap<>();
            mp.put("error",error);
            return ResponseEntity.badRequest().body(mp);
        }

    }


    @GetMapping("/getById")
    public ResponseEntity<Map<String,Object>> getEntityById( @RequestParam Long id, Authentication authentication )
    {

        try {
            ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
            Organization entity = (Organization)organizationService.getOrgById(id);
            return ResponseEntity.ok(JsonRepHelper.getEntityToMap(entity));
        }catch (Exception ex ){
            String error =  ex.getMessage();
            Map<String,Object> mp = new HashMap<>();
            mp.put("error",error);
            return ResponseEntity.badRequest().body(mp);
        }

    }



}
