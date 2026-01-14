package com.momentus.foundation.common.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/common")
public class CountryStateController {

  @GetMapping("/getAllCountries")
  public ResponseEntity<Map<String, String>> getAllCountries() {
    Map<String, String> countries = new HashMap<>();
    countries.put("India", "India");
    countries.put("USA", "USA");
    return ResponseEntity.ok(countries);
  }

  @GetMapping("/getAllStates")
  public ResponseEntity<Map<String, String>> getSates(@RequestParam String country) {
    Map<String, String> states = new HashMap<>();
    if ("India".equals(country)) {
      states.put("Karnataka", "Karnataka");
      states.put("Kerala", "Kerala");
    } else {
      states.put("Georgia", "NewJersey");
      states.put("NewJersey", "NewJersey");
    }

    return ResponseEntity.ok(states);
  }
}
