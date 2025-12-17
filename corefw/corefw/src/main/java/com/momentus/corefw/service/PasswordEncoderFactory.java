package com.momentus.corefw.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderFactory {

    private final PasswordEncoder passwordEncoder;

    public PasswordEncoderFactory(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public PasswordEncoder getPasswordEncoder()
    {
        return getPasswordEncoder();
    }
}
