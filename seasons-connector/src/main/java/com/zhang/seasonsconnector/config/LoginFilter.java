package com.zhang.seasonsconnector.config;

import com.zhang.seasonsconnector.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private static final int nameLen = 30;
    private static final int pwdLen = 16;
    private static final int phoneLen = 11;
    private static final int codeLen = 6;

    public LoginFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("请求的方式不支持: " + request.getMethod());
        }

        String principal;
        String credential;
        String ways = request.getParameter("ways");
        ways = ways == null ? "normal" : ways;
        String type = request.getParameter("type");
        type = type == null ? "user" : type;


        if (ways.equals("code")) {
            principal = request.getParameter("phone");
            credential = request.getParameter("code");

            if (principal == null || principal.length() != phoneLen) {
                throw new UsernameNotFoundException("用户名或密码错误");
            }
            if (credential == null || credential.length() != codeLen) {
                throw new BadCredentialsException("用户名或密码错误");
            }

        } else {
            principal = request.getParameter("name");
            credential = request.getParameter("password");

            if (principal == null || principal.length() > nameLen || principal.length() <= 0) {
                throw new UsernameNotFoundException("用户名或密码错误");
            }
            if (credential == null || credential.length() > pwdLen || credential.length() <= 0) {
                throw new BadCredentialsException("用户名或密码错误");
            }
        }

        SeasonsAuthenticationToken token = new SeasonsAuthenticationToken(credential, principal);
        token.setType(type);
        token.setWays(ways);
        setDetails(request, token);
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SeasonsAuthenticationToken token = (SeasonsAuthenticationToken) authResult;
        String jwtToken = JwtUtils.generateToken(token);
        response.addHeader("token", jwtToken);
    }
}
