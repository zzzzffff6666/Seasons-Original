package com.zhang.seasonsconnector.config;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SeasonsAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Map<String, Object> model = new HashMap<>();
        model.put("result", e.getMessage());
        response.getWriter().println(model);
        response.getWriter().flush();
    }
}
