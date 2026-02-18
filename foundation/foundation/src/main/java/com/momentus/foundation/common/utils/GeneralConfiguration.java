package com.momentus.foundation.common.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.general")
public class GeneralConfiguration {

  private Boolean erroronmissinglabel;

  public Boolean getErroronmissinglabel() {
    return erroronmissinglabel;
  }

  public void setErroronmissinglabel(Boolean erroronmissinglabel) {
    this.erroronmissinglabel = erroronmissinglabel;
  }
}
