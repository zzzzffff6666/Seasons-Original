package com.zhang.seasonsconnector.config;

import com.zhang.seasonsconnector.model.Account;
import com.zhang.seasonsconnector.service.SeasonsUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LoginProvider implements AuthenticationProvider {

    @Autowired
    private SeasonsUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.isAuthenticated()) {
            return authentication;
        }
        SeasonsAuthenticationToken authToken = (SeasonsAuthenticationToken) authentication;

        String ways = authToken.getWays();
        String type = authToken.getType();
        if (ways.equals("code")) {
            String phone = authToken.getName();
            String code = authToken.getCredentials().toString();
            Account a = (Account) userDetailsService.loadUserByUsername(phone);

            return new SeasonsAuthenticationToken(a.getId(), type, phone, code, a.getAuthorities());
        } else {
            String name = authToken.getName().trim();
            String password = authToken.getCredentials().toString().trim();

            Account a = (Account) userDetailsService.loadUserByUsername(name, type);
            if (a == null) {
                throw new UsernameNotFoundException("用户名或密码错误");
            }

            if (!password.equals(a.getPassword())) {
                throw new BadCredentialsException("用户名或密码错误");
            }
            return new SeasonsAuthenticationToken(a.getId(), type, name, password, a.getAuthorities());
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
