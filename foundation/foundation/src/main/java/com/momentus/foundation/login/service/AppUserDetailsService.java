package com.momentus.foundation.login.service;

import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.accessgroup.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository users;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = users.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userId));
        List<GrantedAuthority> authorities = getGrantedAuthorities(/*user.getRoleList()*/ new ArrayList<String>());
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
