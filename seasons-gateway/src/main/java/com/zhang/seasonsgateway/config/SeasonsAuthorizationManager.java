package com.zhang.seasonsgateway.config;

import com.zhang.seasonsgateway.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Component
public class SeasonsAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private static final String loginUrl = "http://localhost:8080/login";

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication,
                                             AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        ServerHttpResponse response = authorizationContext.getExchange().getResponse();
        if (request.getMethod() == HttpMethod.GET || request.getPath().toString().equals("/login")) {
            log.info(request.getPath().toString());
            return Mono.just(new AuthorizationDecision(true));
        }

        String token = request.getHeaders().getFirst("token");
        if (token == null || !JwtUtils.checkToken(token)) {
            log.info("redirect");
            response.setStatusCode(HttpStatus.SEE_OTHER);
            response.getHeaders().setLocation(URI.create(loginUrl));
            response.setComplete();
            return Mono.just(new AuthorizationDecision(false));
        }
        return Mono.just(new AuthorizationDecision(true));
    }

    @Override
    public Mono<Void> verify(Mono<Authentication> authentication, AuthorizationContext object) {
        return check(authentication, object)
                .filter(AuthorizationDecision::isGranted)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new AccessDeniedException("当前用户没有访问权限"))))
                .flatMap(d -> Mono.empty());
    }
}
