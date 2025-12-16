package com.momentus.foundation.organization.controller;

import com.momentus.foundation.common.JsonRepHelper;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.organization.model.OrgProfile;
import com.momentus.foundation.organization.model.Organization;
import com.momentus.foundation.organization.service.OrgProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/orgprofile")
public class OrgProfileController {

    @Autowired
    OrgProfileService orgProfileService;

    @Autowired
    ApplicationContextHelper applicationContextHelper ;

    @PostMapping("/create")
    public ResponseEntity<Map<String,Object>> create(@RequestBody Map<String,Object> request, Authentication authentication) {

        OrgProfile orgProfile  = JsonRepHelper.getEntityFromMap(request, OrgProfile.class);
        ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
        TransactionResponse transactionResponse = orgProfileService.save(orgProfile,context);
        return ResponseEntity.ok(transactionResponse.convertToMap());

    }
    @GetMapping("/getById")
    public ResponseEntity<Map<String,Object>> getById(@RequestParam Long id, Authentication authentication) {
        try {
            ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
            OrgProfile orgProfile = orgProfileService.getById(id, context);
            return ResponseEntity.ok(JsonRepHelper.getEntityToMap(orgProfile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



}
