package com.momentus.foundation.common.controller;

import com.momentus.foundation.orgsignup.service.OrgSignupService;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/common")
public class CountryStateController {

  @Autowired OrgSignupService orgSignupService;

  @GetMapping("/getAllCountries")
  public ResponseEntity<Map<String, String>> getAllCountries() {
    Map<String, String> countries = new LinkedHashMap<>();

    for (String countryCode : Locale.getISOCountries()) {
      Locale locale = new Locale("", countryCode);
      countries.put(locale.getDisplayCountry(), locale.getDisplayCountry());
    }
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

  @GetMapping("/getAllSectors")
  public ResponseEntity<Map<String, String>> getAllSectors() {
    Map<String, String> sectors = orgSignupService.getAllSectorsforUI();

    return ResponseEntity.ok(sectors);
  }
}
