package com.msjf.seasons.bean;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("type") != null) {
            // 已登录，放行。。
            return true;
        } else {
            System.out.println("你还没登录，没有权限");
            // 未登录，拦截 返回login
            response.sendRedirect("/login");
            return false;
        }
    }
}
