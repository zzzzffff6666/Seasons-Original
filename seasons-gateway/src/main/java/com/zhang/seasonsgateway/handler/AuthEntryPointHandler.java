package com.zhang.seasonsgateway.handler;

import com.alibaba.fastjson.JSONObject;
import com.zhang.seasonsgateway.model.AjaxResult;
import com.zhang.seasonsgateway.model.APICode;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

@Slf4j
@Component
public class AuthEntryPointHandler implements ServerAuthenticationEntryPoint {

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException e) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        Map<String, Object> ajaxResult = AjaxResult.error(APICode.UNAUTHORIZED, e.getMessage());
        String body = JSONObject.toJSONString(ajaxResult);
        DataBuffer wrap = exchange.getResponse().bufferFactory().wrap(body.getBytes(CharsetUtil.UTF_8));
        return exchange.getResponse().writeWith(Flux.just(wrap));
    }
}
