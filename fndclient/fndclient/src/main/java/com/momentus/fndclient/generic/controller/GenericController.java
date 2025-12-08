package com.momentus.fndclient.generic.controller;

import com.momentus.fndclient.generic.service.GenericService;
import com.momentus.foundation.common.JsonRepHelper;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import com.momentus.foundation.organization.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/generic")
public class GenericController
{
    @Autowired
    GenericService genericService;

    @Autowired
    ApplicationContextHelper applicationContextHelper ;

    @PreAuthorize("hasAuthority('custwr') or hasAuthority('adm')")
    @PostMapping("/create")
    public ResponseEntity<Map<String,String>> createEntity(@RequestBody Map<String,Object> entityMap, @RequestParam String entityType, Authentication authentication )
    {

        if("Customer".equalsIgnoreCase(entityType))
        {
            ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
            OrgBasedEntity entity = JsonRepHelper.getEntityFromMap(entityMap,com.momentus.fndclient.customer.model.Customer.class);
            genericService.createEntity(entity,context);
        }
        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Customer created successfully");
        return ResponseEntity.ok(response);

    }
}
