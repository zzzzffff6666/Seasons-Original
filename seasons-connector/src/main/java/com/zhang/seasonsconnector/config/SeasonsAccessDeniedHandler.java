package com.zhang.seasonsconnector.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class SeasonsAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e)
            throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Map<String, Object> model = new HashMap<>();
        model.put("result", e.getMessage());
        response.getWriter().println(model);
        response.getWriter().flush();
    }
}
