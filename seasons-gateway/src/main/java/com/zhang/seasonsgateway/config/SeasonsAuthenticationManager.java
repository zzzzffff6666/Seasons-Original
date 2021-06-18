package com.zhang.seasonsgateway.config;

import com.zhang.seasonsgateway.model.Account;
import com.zhang.seasonsgateway.model.SeasonsAuthenticationToken;
import com.zhang.seasonsgateway.service.SeasonsReactiveUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractUserDetailsReactiveAuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Component
public class SeasonsAuthenticationManager extends AbstractUserDetailsReactiveAuthenticationManager {

    private final Scheduler scheduler = Schedulers.boundedElastic();

    @Autowired
    private SeasonsReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if (authentication.isAuthenticated()) {
            return Mono.just(authentication);
        }

        SeasonsAuthenticationToken token = (SeasonsAuthenticationToken) authentication;
        final String username = token.getName();
        final String password = token.getCredentials().toString();
        final String type = token.getType();

        // 省略业务代码
        return retrieveUser(username, type)
                .publishOn(scheduler)
                .filter(u -> password.equals(u.getPassword()))
                .switchIfEmpty(Mono.defer(() -> Mono.error(new BadCredentialsException("用户名或密码错误"))))
                .flatMap(Mono::just)
                .map(u -> {
                    Account a = (Account) u;
                    return new SeasonsAuthenticationToken(a.getUsername(), a.getPassword(), a.getType(), a.getId(), a.getAuthorities());
                });
    }

    @Override
    protected Mono<UserDetails> retrieveUser(String username) {
        return null;
    }

    public Mono<UserDetails> retrieveUser(String username, String accountType) {
        return userDetailsService.findByUsername(username, accountType);
    }
}
