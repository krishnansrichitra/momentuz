package com.momentus.foundation.login.service;

import com.momentus.corefw.service.PasswordEncoderFactory;
import com.momentus.foundation.accessgroup.model.Role;
import com.momentus.foundation.accessgroup.model.User;
import com.momentus.foundation.accessgroup.model.UserRoles;
import com.momentus.foundation.accessgroup.repository.UserRepository;
import com.momentus.foundation.common.context.ApplicationContext;
import com.momentus.foundation.common.transaction.TransactionResponse;
import com.momentus.foundation.generic.service.MapToEntityMapper;
import com.momentus.foundation.login.model.MomLoggedInUser;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository users;

    @Autowired
    MapToEntityMapper mapToEntityMapper;

    private final PasswordEncoder passwordEncoder;

    public AppUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = users.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userId));

        List<String > userAccessCodes = user.getUserRoles().stream().map(UserRoles::getRole).map(Role::getAccessCodes).collect(Collectors.toList());
        List<String> indAccessCodes = userAccessCodes.stream().flatMap( entry -> Arrays.stream(entry.split(",")) ).collect(Collectors.toList());
        List<GrantedAuthority> authorities = getGrantedAuthorities(indAccessCodes);
        org.springframework.security.core.userdetails.User loggedUser =  (org.springframework.security.core.userdetails.User )org.springframework.security.core.userdetails.User.builder()
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

    public TransactionResponse createUser(Map<String,Object> userMap, ApplicationContext context)
    {
      User user =new User();

        mapToEntityMapper.populateFromMap(userMap,user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setOrgId(context.getOrganization());
        user.setCreatedBy(context.getLoggedInUser());
        user.setCreatedTime(LocalDateTime.now());
        users.save(user);
        return  new TransactionResponse(TransactionResponse.RESPONSE_STATUS.SUCCESS);

    }

}
