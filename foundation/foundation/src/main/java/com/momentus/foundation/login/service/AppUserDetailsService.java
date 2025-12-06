package com.momentus.foundation.login.service;

import com.momentus.foundation.accessgroup.model.Role;
import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.accessgroup.model.UserRoles;
import com.momentus.foundation.accessgroup.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = users.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userId));

        List<String > userAccessCodes = user.getUserRoles().stream().map(UserRoles::getRole).map(Role::getAccessCodes).collect(Collectors.toList());
        List<String> indAccessCodes = userAccessCodes.stream().flatMap( entry -> Arrays.stream(entry.split(",")) ).collect(Collectors.toList());
        List<GrantedAuthority> authorities = getGrantedAuthorities(indAccessCodes);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUserId())
                .password(user.getPassword()) // password is stored as BCrypt hash in DB
                .authorities(authorities)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }


    private List<GrantedAuthority> getGrantedAuthorities (List<String> roles) {
        List<GrantedAuthority> result = new ArrayList<>();
        for (String role : roles) {
            GrantedAuthority authority = new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return role;
                }
            };
            result.add(authority);
        }
        return result;
    }
}
