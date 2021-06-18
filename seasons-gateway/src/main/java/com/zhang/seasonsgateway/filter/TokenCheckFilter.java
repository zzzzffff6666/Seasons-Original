package com.zhang.seasonsgateway.filter;

import com.zhang.seasonsgateway.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
public class TokenCheckFilter implements GlobalFilter, Ordered {

    private static final String loginUrl = "http://localhost:8080/login";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("first filter");

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        if (request.getMethod() == HttpMethod.GET || request.getPath().toString().equals("/login")) {
            log.info(request.getPath().toString());
            return chain.filter(exchange);
        }

        String token = request.getHeaders().getFirst("token");
        if (token == null || !JwtUtils.checkToken(token)) {
            log.info("redirect");
            response.setStatusCode(HttpStatus.SEE_OTHER);
            response.getHeaders().setLocation(URI.create(loginUrl));
            return response.setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 300;
    }
}
