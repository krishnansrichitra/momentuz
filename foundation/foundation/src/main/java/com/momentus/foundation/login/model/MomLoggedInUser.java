package com.momentus.foundation.login.model;

import com.momentus.foundation.organization.model.Organization;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class MomLoggedInUser extends User {

    private Organization loggedInOrg ;

    public MomLoggedInUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public MomLoggedInUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public MomLoggedInUser(User user){
        super(user.getUsername(),user.getPassword(),user.isEnabled(),user.isAccountNonExpired(),user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
    }

    public Organization getLoggedInOrg() {
        return loggedInOrg;
    }

    public void setLoggedInOrg(Organization loggedInOrg) {
        this.loggedInOrg = loggedInOrg;
    }
}
