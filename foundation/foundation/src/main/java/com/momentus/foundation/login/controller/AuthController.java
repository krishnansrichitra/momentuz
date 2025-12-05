package com.momentus.foundation.login.controller;


import com.momentus.corefw.auth.JwtTokenProvider;
import com.momentus.foundation.login.dto.AuthRequest;
import com.momentus.foundation.login.dto.AuthResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    public AuthController(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        // Perform authentication (will throw if invalid)
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Extract roles/authorities to include in token (optional)
        @SuppressWarnings("unchecked")
        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Create token (subject = username)
        String token = jwtTokenProvider.createToken(authentication.getName(), roles);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/hello")  // Handles GET requests to /hello
    public String sayHello() {
        return "Hello World";
    }

}
