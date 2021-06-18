package com.zhang.seasonsgateway.handler;

import com.alibaba.fastjson.JSONObject;
import com.zhang.seasonsgateway.model.AjaxResult;
import com.zhang.seasonsgateway.model.APICode;
import io.netty.util.CharsetUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class LoginFailureHandler implements ServerAuthenticationFailureHandler {

    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        return writeErrorMessage(response, exception.getMessage());
    }

    private Mono<Void> writeErrorMessage(ServerHttpResponse response, String message) {
        String result = JSONObject.toJSONString(AjaxResult.error(APICode.UNAUTHORIZED, message));
        DataBuffer buffer = response.bufferFactory().wrap(result.getBytes(CharsetUtil.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
