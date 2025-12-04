package com.momentus.foundation.test.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secured")
public class SecuredController {

    @GetMapping("/hello")  // Handles GET requests to /hello
    public String sayHello() {
        return "Hello World";
    }

    @PreAuthorize("hasAuthority('perm4')")
    @GetMapping("/checkP3")  // Handles GET requests to /hello
    public String checkP3() {
        return "has permission";
    }

}


