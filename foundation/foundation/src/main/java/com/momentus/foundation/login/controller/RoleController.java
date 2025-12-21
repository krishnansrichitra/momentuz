package com.momentus.foundation.login.controller;

import com.momentus.foundation.accessgroup.model.Role;
import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import com.momentus.foundation.generic.service.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    ApplicationContextHelper applicationContextHelper ;

    @Autowired
    GenericService genericService;

    private static final Logger log = LoggerFactory.getLogger(RoleController.class);

    @PostMapping("/create")
    public ResponseEntity<Map<String,String>> createEntity(@RequestBody Map<String,Object> entityMap, Authentication authentication )
    {

        try {

            ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
            //OrgBasedEntity entity = JsonRepHelper.getEntityFromMap(entityMap, (Class<? extends OrgBasedEntity>) Class.forName(fullPackage));
            Role entity = new Role();
            genericService.createOrUpdateEntity(entityMap, entity, context);
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Role created successfully");
            log.info("saving object");
            return ResponseEntity.ok(response);
        }catch (Exception ex ){
            String error =  ex.getMessage();
            Map<String,String> mp = new HashMap<>();
            mp.put("error",error);
            return ResponseEntity.badRequest().body(mp);
        }

    }
}
