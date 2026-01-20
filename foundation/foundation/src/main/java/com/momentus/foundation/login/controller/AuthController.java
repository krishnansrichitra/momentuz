package com.momentus.foundation.login.controller;

import com.momentus.corefw.auth.JwtTokenProvider;
import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.login.dto.AuthRequest;
import com.momentus.foundation.login.dto.AuthResponse;
import com.momentus.foundation.login.model.MomLoggedInUser;
import com.momentus.foundation.login.service.AppUserDetailsService;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;

  @Autowired AppUserDetailsService appUserDetailsService;

  @Autowired GeneralMessages generalMessages;

  private static final Logger log = LoggerFactory.getLogger(AuthController.class);

  public AuthController(
      AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
    // Perform authentication (will throw if invalid)
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

    MomLoggedInUser momLoggedInUser = (MomLoggedInUser) authentication.getPrincipal();

    // Extract roles/authorities to include in token (optional)
    @SuppressWarnings("unchecked")
    List<String> roles =
        authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    // Create token (subject = username)
    HashMap<String, Object> supplimentaryInfo = new LinkedHashMap<>();
    supplimentaryInfo.put("loggednOrgCode", momLoggedInUser.getLoggedInOrg().getOrgCode());
    supplimentaryInfo.put("loggednOrgId", (Long) momLoggedInUser.getLoggedInOrg().getId());
    String token = jwtTokenProvider.createToken(authentication.getName(), roles, supplimentaryInfo);

    return ResponseEntity.ok(new AuthResponse(token));
  }

  @GetMapping("/hello") // Handles GET requests to /hello
  public String sayHello() {
    return "Hello World";
  }

  @GetMapping("/resetPassword")
  public ResponseEntity<Map<String, Object>> resetPassword(@RequestParam String email) {

    log.debug("Calling resetPassword by " + email);
    try {

      TransactionResponse response = appUserDetailsService.resetPassword(email);
      log.info("Updating Password");
      return response.getResponseStatus().compareTo(TransactionResponse.RESPONSE_STATUS.FAILURE)
              == 0
          ? ResponseEntity.badRequest().body(response.errorMap())
          : ResponseEntity.ok(response.convertToMap());
    } catch (Exception ex) {
      Map<String, Object> mp = new HashMap<>();
      List<MomentusError> errors = new ArrayList<>();
      MomentusError momentusError =
          new MomentusError(
              GeneralMessages.UNIDIENTIFABLE_ERROR,
              generalMessages.getMessage(GeneralMessages.UNIDIENTIFABLE_ERROR, Locale.US));
      errors.add(momentusError);
      mp.put("errors", errors);
      return ResponseEntity.badRequest().body(mp);
    }
  }
}
