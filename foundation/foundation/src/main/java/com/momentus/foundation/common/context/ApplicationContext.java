package com.momentus.foundation.common.context;

import com.momentus.foundation.organization.model.Organization;
import org.springframework.security.core.Authentication;

import java.util.Map;

public class ApplicationContext {

    String loggedInUser;

    Organization organization;

    public String getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }



}
