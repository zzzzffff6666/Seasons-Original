package com.zhang.seasonsgateway.handler;

import com.alibaba.fastjson.JSONObject;
import com.zhang.seasonsgateway.model.SeasonsAuthenticationToken;
import com.zhang.seasonsgateway.model.AjaxResult;
import com.zhang.seasonsgateway.utils.JwtUtils;
import io.netty.util.CharsetUtil;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class LoginSuccessHandler implements ServerAuthenticationSuccessHandler {

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        SeasonsAuthenticationToken token = (SeasonsAuthenticationToken) authentication;
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();

        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        Map<String, Object> ajaxResult = AjaxResult.success("登录成功！", JwtUtils.generateToken(token));
        ajaxResult.put("type", token.getType());
        ajaxResult.put("id", String.valueOf(token.getId()));
        String body = JSONObject.toJSONString(ajaxResult);
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(CharsetUtil.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
