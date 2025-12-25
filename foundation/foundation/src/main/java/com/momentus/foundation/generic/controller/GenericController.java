package com.momentus.foundation.generic.controller;

import com.momentus.foundation.common.JsonRepHelper;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.entity.service.EntityService;
import com.momentus.foundation.generic.service.GenericService;
import com.momentus.foundation.organization.model.OrgBasedEntity;
import com.momentus.foundation.organization.model.Organization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    EntityService entityService;

    private static final Logger log = LoggerFactory.getLogger(GenericController.class);






  //  @PreAuthorize("hasAuthority('custwr') or hasAuthority('adm')")
    @PostMapping("/createOrUpdate")
    public ResponseEntity<Map<String,Object>> createEntity(@RequestBody Map<String,Object> entityMap, @RequestParam String entityType, Authentication authentication )
    {

        try {
            String fullPackage = entityService.getFullPackage(entityType);
            ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
            //OrgBasedEntity entity = JsonRepHelper.getEntityFromMap(entityMap, (Class<? extends OrgBasedEntity>) Class.forName(fullPackage));
            OrgBasedEntity entity = (OrgBasedEntity) Class.forName(fullPackage).newInstance();
            TransactionResponse transactionResponse = genericService.createOrUpdateEntity(entityMap,entity,context);
            if(transactionResponse.getResponseStatus().compareTo(TransactionResponse.RESPONSE_STATUS.FAILURE) ==0 ) {
                return ResponseEntity.badRequest().body(transactionResponse.errorMap());
            }
            return ResponseEntity.ok(transactionResponse.convertToMap());
        }catch (Exception ex ){
            String error =  ex.getMessage();
            Map<String,Object> mp = new HashMap<>();
            mp.put("error",error);
            return ResponseEntity.badRequest().body(mp);
        }

    }

    @PostMapping("/delete")
    public ResponseEntity<Map<String,Object>> deleteEntity(@RequestBody Map<String,Object> entityMap, @RequestParam String entityType, Authentication authentication )
    {

        try {
            String fullPackage = entityService.getFullPackage(entityType);
            ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
            OrgBasedEntity entity = (OrgBasedEntity) Class.forName(fullPackage).newInstance();
            TransactionResponse transactionResponse = genericService.deleteEntity(entityMap,entity,context);
            if(transactionResponse.getResponseStatus().compareTo(TransactionResponse.RESPONSE_STATUS.FAILURE) ==0 ) {
                return ResponseEntity.badRequest().body(transactionResponse.errorMap());
            }
            return ResponseEntity.ok(transactionResponse.convertToMap());
        }catch (Exception ex ){
            String error =  ex.getMessage();
            Map<String,Object> mp = new HashMap<>();
            mp.put("error",error);
            return ResponseEntity.badRequest().body(mp);
        }

    }


    @PostMapping("/getByBusinessKey")
    public ResponseEntity<Map<String,Object>> geByBusinessKey(@RequestBody Map<String,Object> entityMap, @RequestParam String entityType, Authentication authentication )
    {

        try {
            String fullPackage = entityService.getFullPackage(entityType);
            ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
            OrgBasedEntity entity =   genericService.findByBusinessKey(entityMap,(Class<? extends OrgBasedEntity>) Class.forName(fullPackage),context);
            return ResponseEntity.ok(JsonRepHelper.getEntityToMap(entity));
        }catch (Exception ex ){
            String error =  ex.getMessage();
            Map<String,Object> mp = new HashMap<>();
            mp.put("error",error);
            return ResponseEntity.badRequest().body(mp);
        }

    }


    @GetMapping("/getById")
    public ResponseEntity<Map<String,Object>> getEntityById( @RequestParam String entityType, @RequestParam Long id, Authentication authentication )
    {

        try {
            String fullPackage = entityService.getFullPackage(entityType);
            ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
            OrgBasedEntity entity = genericService.findById(id, (Class<? extends OrgBasedEntity>) Class.forName(fullPackage),context);
            return ResponseEntity.ok(JsonRepHelper.getEntityToMap(entity));
        }catch (Exception ex ){
            String error =  ex.getMessage();
            Map<String,Object> mp = new HashMap<>();
            mp.put("error",error);
            return ResponseEntity.badRequest().body(mp);
        }

    }




}
