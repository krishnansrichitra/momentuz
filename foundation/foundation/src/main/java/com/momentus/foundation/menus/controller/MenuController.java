package com.momentus.foundation.menus.controller;

import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.context.ApplicationContextHelper;
import com.momentus.foundation.menus.dto.MenuDTOHelper;
import com.momentus.foundation.menus.dto.MenuSetDTO;
import com.momentus.foundation.menus.model.MenuSet;
import com.momentus.foundation.menus.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    ApplicationContextHelper applicationContextHelper ;

    @Autowired
    MenuService menuService;

    @Autowired
    MenuDTOHelper menuDTOHelper;

    @GetMapping("/getMenus")
    public ResponseEntity<MenuSetDTO> getMenus(Authentication authentication)
    {
        ApplicationContext context = applicationContextHelper.generateAppContext(authentication);
        MenuSetDTO set = menuService.getMenuSet(context.getOrganization().getId(),context.getLocale());
        return ResponseEntity.ok(set);

    }
}
