package com.momentus.foundation.login.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Genpassword {

  public static void main(String[] args) {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    String rawPassword = "password";
    String hashed = encoder.encode(rawPassword);
    System.out.println(hashed);
  }
}
