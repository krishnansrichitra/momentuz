package com.momentus.foundation.common.context;

import com.momentus.foundation.organization.model.Organization;
import com.momentus.foundation.organization.service.OrganizationService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ApplicationContextHelper {

  @Autowired OrganizationService organizationService;

  public ApplicationContext generateAppContext(Authentication authentication) {
    Map<String, Object> supplimentaryInfo = (Map) authentication.getDetails();
    ApplicationContext applicationContext = new ApplicationContext();
    if (supplimentaryInfo == null || supplimentaryInfo.keySet().size() == 0) {

    } else {
      Organization organization =
          organizationService.getOrgById(
              Long.valueOf((Integer) supplimentaryInfo.get("loggednOrgId")));
      applicationContext.setOrganization(organization);
      applicationContext.setLoggedInUser(authentication.getName());
    }
    return applicationContext;
  }
}
