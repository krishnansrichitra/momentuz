package com.momentus.foundation.common.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "server.email")
public class EmailProperties {
  private String user;

  private String password;

  private Integer smtpport;

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getSmtpport() {
    return smtpport;
  }

  public void setSmtpport(Integer smtpport) {
    this.smtpport = smtpport;
  }
}
