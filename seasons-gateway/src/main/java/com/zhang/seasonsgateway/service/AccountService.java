package com.zhang.seasonsgateway.service;

import com.zhang.seasonsgateway.controller.AdminRemote;
import com.zhang.seasonsgateway.controller.MagRemote;
import com.zhang.seasonsgateway.controller.UserRemote;
import com.zhang.seasonsgateway.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {
    private static final List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>() {{
        add(new SimpleGrantedAuthority("ROLE_ROOT"));
        add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        add(new SimpleGrantedAuthority("ROLE_USER"));
        add(new SimpleGrantedAuthority("ROLE_MAG"));
    }};

    @Autowired
    private AdminRemote adminRemote;
    @Autowired
    private MagRemote magRemote;
    @Autowired
    private UserRemote userRemote;

    public Mono<UserDetails> searchForAccount(String accountType, String param) {
        Mono<Object> result;
        switch (accountType) {
            case "user":
                result = userRemote.getUser(param);
                break;
            case "admin":
                result = adminRemote.getAdmin(param);
                break;
            default:
                result = magRemote.getMag(param);
                break;
        }
        return result.map(object -> {
            Map<String, Object> info = (Map<String, Object>) object;
            if (info.get("error") != null) {
                return Mono.defer(() -> Mono.error(new UsernameNotFoundException("用户名或密码错误")));
            }
            Account account = new Account();
            account.setId(Integer.parseInt(info.get("id").toString()));
            account.setType(accountType);
            account.setUsername(info.get("name").toString());
            account.setPassword(info.get("password").toString());
            account.setPhone(info.get("phone").toString());
            account.setAuthorities(getAuths(accountType));
            return account;
        }).cast(UserDetails.class);
    }

    public static List<SimpleGrantedAuthority> getAuths(String type) {
        List<SimpleGrantedAuthority> auths = new ArrayList<>();
        switch (type) {
            case "user":
                auths.add(authorities.get(2));
                break;
            case "admin":
                auths.add(authorities.get(1));
                break;
            case "mag":
                auths.add(authorities.get(3));
                break;
            case "root":
                auths.addAll(authorities);
                break;
        }
        return auths;
    }
}
