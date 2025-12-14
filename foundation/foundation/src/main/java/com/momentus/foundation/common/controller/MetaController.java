package com.momentus.foundation.common.controller;

import com.momentus.foundation.common.JsonRepHelper;
import com.momentus.foundation.common.model.BaseEntity;
import com.momentus.foundation.entity.model.Entity;
import com.momentus.foundation.entity.service.EntityService;
import com.momentus.foundation.organization.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/meta")
public class MetaController {

    @Autowired
    EntityService entityService;

    @GetMapping("/getBasicMap")
    public ResponseEntity<Map<String,Object>> getBasicMap(@RequestParam String entity) {

        try {
            String fullPackage = entityService.getFullPackage(entity);
            BaseEntity obj = (BaseEntity)Class.forName(fullPackage).newInstance();
            return ResponseEntity.ok(JsonRepHelper.getMapRepresentation(obj));
        }catch ( Exception ex)
        {
            return ResponseEntity.badRequest().body(new HashMap<>());
        }

    }
}
