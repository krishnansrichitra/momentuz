package com.momentus.foundation.menus.controller;

import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orgprofile")
public class MenuController {

    @Autowired
    ApplicationContextHelper applicationContextHelper ;


    public ResponseEntity<Object> getMenus(Authentication authentication)
    {
        ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
    }
}
