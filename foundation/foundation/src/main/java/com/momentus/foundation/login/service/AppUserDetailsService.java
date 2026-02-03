package com.momentus.foundation.login.service;

import com.momentus.corefw.auth.PasswordGenerator;
import com.momentus.foundation.accessgroup.model.Role;
import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.accessgroup.model.UserRoles;
import com.momentus.foundation.accessgroup.repository.UserRepository;
import com.momentus.foundation.common.GeneralMessages;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.MomentusError;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.common.utils.EmailProperties;
import com.momentus.foundation.common.utils.EmailSender;
import com.momentus.foundation.generic.service.MapToEntityMapper;
import com.momentus.foundation.login.model.MomLoggedInUser;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

  @Autowired UserRepository users;

  @Autowired MapToEntityMapper mapToEntityMapper;

  private final PasswordEncoder passwordEncoder;

  private final EmailProperties emailProperties;

  private static final Logger log = LoggerFactory.getLogger(AppUserDetailsService.class);

  @Autowired GeneralMessages generalMessages;

  public AppUserDetailsService(PasswordEncoder passwordEncoder, EmailProperties emailProperties) {
    this.passwordEncoder = passwordEncoder;
    this.emailProperties = emailProperties;
  }

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    User user =
        users
            .findByUserId(userId)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userId));

    List<String> userAccessCodes =
        user.getUserRoles().stream()
            .map(UserRoles::getRole)
            .map(Role::getAccessCodes)
            .collect(Collectors.toList());
    List<String> indAccessCodes =
        userAccessCodes.stream()
            .flatMap(entry -> Arrays.stream(entry.split(",")))
            .collect(Collectors.toList());
    List<GrantedAuthority> authorities = getGrantedAuthorities(indAccessCodes);
    org.springframework.security.core.userdetails.User loggedUser =
        (org.springframework.security.core.userdetails.User)
            org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserId())
                .password(user.getPassword()) // password is stored as BCrypt hash in DB
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    MomLoggedInUser momLoggedInUser = new MomLoggedInUser(loggedUser);
    momLoggedInUser.setLoggedInOrg(user.getOrgId());
    return momLoggedInUser;
  }

  private List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
    List<GrantedAuthority> result = new ArrayList<>();
    for (String role : roles) {
      GrantedAuthority authority =
          new GrantedAuthority() {
            @Override
            public String getAuthority() {
              return role;
            }
          };
      result.add(authority);
    }
    return result;
  }

  public TransactionResponse createUser(Map<String, Object> userMap, ApplicationContext context) {
    User user = new User();

    mapToEntityMapper.populateFromMap(userMap, user, context);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    //   user.setOrgId(context.getOrganization());
    user.setCreatedBy(context.getLoggedInUser());
    user.setCreatedTime(LocalDateTime.now());
    users.save(user);
    return new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
  }

  public TransactionResponse updatePassword(
      String userId,
      String currentPassword,
      String newPassword,
      String newPasswordConfirm,
      ApplicationContext context) {
    if (!newPassword.equalsIgnoreCase(newPasswordConfirm)) {
      MomentusError momentusError =
          new MomentusError(
              GeneralMessages.PASSWORDS_NOT_MATCH,
              generalMessages.getMessage(GeneralMessages.PASSWORDS_NOT_MATCH, context.getLocale()));
      return new TransactionResponse(
          TransactionResponse.RESPONSE_STATUS.FAILURE, List.of(momentusError), null);
    }
    Boolean validPassword = PasswordGenerator.isValidPassword(newPassword);
    if (!validPassword) {
      MomentusError momentusError =
          new MomentusError(
              GeneralMessages.PASSWORD_NOT_VALID,
              generalMessages.getMessage(GeneralMessages.PASSWORD_NOT_VALID, context.getLocale()));
      return new TransactionResponse(
          TransactionResponse.RESPONSE_STATUS.FAILURE, List.of(momentusError), null);
    }
    User user = users.findByUserId(userId).orElse(null);
    if (user != null) {

      if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
        MomentusError momentusError =
            new MomentusError(
                GeneralMessages.PASSWORD_ENTERED_WRONG,
                generalMessages.getMessage(
                    GeneralMessages.PASSWORD_ENTERED_WRONG, context.getLocale()));
        return new TransactionResponse(
            TransactionResponse.RESPONSE_STATUS.FAILURE, List.of(momentusError), null);
      }
      user.setSystemCreated(false);
      user.setPassword(passwordEncoder.encode(newPassword));
    }
    users.save(user);
    TransactionResponse response =
        new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
    response.setResponseMesage(
        generalMessages.getMessage(
            GeneralMessages.PASSWORD_UPDATED_SUCCESFULLY, context.getLocale()));
    return response;
  }

  public TransactionResponse resetPassword(String userId) {
    User user = users.findByUserId(userId).orElse(null);
    String randomPassword = PasswordGenerator.generatePassword(8);
    user.setPassword(passwordEncoder.encode(randomPassword));
    user.setSystemCreated(true);
    TransactionResponse response =
        new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);
    emailPassword(user.getEmail(), randomPassword);
    response.setResponseMesage("Password updated and emailed");
    return response;
  }

  private void emailPassword(String email, String newPassword) {
    System.out.println("email=" + newPassword);
    log.debug("Emailing password=" + newPassword);
    EmailSender.sendEmail(
        "smtpout.secureserver.net",
        emailProperties.getSmtpport(),
        emailProperties.getUser(),
        emailProperties.getPassword(),
        email,
        "Password for MomentusOne",
        " Please find your credentials to log in to Momentusone. User Id: "
            + email
            + " password: "
            + newPassword
            + "<p>"
            + " It is recommended to change the password once you loging",
        new HashMap<>());
  }

  public String makePasswordForPrimaryUser(String email) {
    String randomPassword = PasswordGenerator.generatePassword(8);
    System.out.println("password = " + randomPassword);
    emailPassword(email, randomPassword);
    return passwordEncoder.encode(randomPassword);
  }
}
