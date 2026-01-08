package com.momentus.foundation.ui.lookup.controller;


import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import com.momentus.foundation.entity.service.EntityService;
import com.momentus.foundation.generic.controller.GenericController;
import com.momentus.foundation.ui.lookup.service.LookupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lookup")
public class LookupController {

  @Autowired
  ApplicationContextHelper applicationContextHelper;

  @Autowired
  LookupService lookupService;

  @Autowired
  EntityService entityService;

    private static final Logger log = LoggerFactory.getLogger(LookupController.class);

    @GetMapping("fvdropdowns")
    public ResponseEntity<Map<String,String>> getFiniteValueDropdowns(@RequestParam String fvGroup, Authentication authentication)
    {
        log.debug("getting getFiniteValueDropdowns for " + fvGroup);
        ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
        Map<String,String>  retValue  = lookupService.getFValuesforDropDown(fvGroup,context.getLocale());
        return ResponseEntity.ok(retValue);

    }


    @GetMapping("typeaheadsearch")
    public ResponseEntity<List<String>> getTypeAheadValues(@RequestParam String entity, String field, String value,Authentication authentication){
        log.debug("getting getTypeAheadValues for " + entity , field , value);
        ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
        List<String> retValues = lookupService.getTypeAheadValues(context,entity,field,value);
      return ResponseEntity.ok(retValues);

    }

    @GetMapping("getEntityDropDowns")
    public ResponseEntity<Map<String,String>> getEntityDropDowns(Authentication authentication)
    {
        log.debug("getting getEntityDropDowns ");
        ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
        Map<String,String> result = entityService.getAllEntities(context.getLocale());
        return ResponseEntity.ok(result);

    }


}
