package com.zhang.seasonsgateway.service;

import com.zhang.seasonsgateway.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class SeasonsReactiveUserDetailsService implements ReactiveUserDetailsService {
    @Autowired
    private AccountService accountService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return findByUsername(username, "user");
    }

    public Mono<UserDetails> findByUsername(String username, String accountType) {
        return accountService.searchForAccount(accountType, username)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException("用户名或密码错误"))));
    }

}
