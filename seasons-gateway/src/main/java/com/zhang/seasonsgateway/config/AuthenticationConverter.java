package com.zhang.seasonsgateway.config;

import com.zhang.seasonsgateway.model.SeasonsAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.server.authentication.ServerFormLoginAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class AuthenticationConverter extends ServerFormLoginAuthenticationConverter {

    private static final String usernameParameter = "username";
    private static final String passwordParameter = "password";
    private static final String typeParameter = "type";

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        String username = exchange.getRequest().getHeaders().getFirst(usernameParameter);
        String password = exchange.getRequest().getHeaders().getFirst(passwordParameter);
        String type = exchange.getRequest().getHeaders().getFirst(typeParameter);
        if (username == null || password == null || type == null)
            return Mono.defer(() -> Mono.error(new UsernameNotFoundException("参数缺失")));
        log.info(username + password + type);
        return Mono.just(new SeasonsAuthenticationToken(username, password, type));

//        return exchange.getFormData()
//                .map(data -> {
//                    log.info(data.toString());
//                    String username = data.getFirst(usernameParameter);
//                    String password = data.getFirst(passwordParameter);
//                    String type = data.getFirst(typeParameter);
//                    if (type == null) type = "user";
//                    return new SeasonsAuthenticationToken(username, password, type);
//                });
    }
}
