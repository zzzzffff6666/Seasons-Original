package com.zhang.seasonsgateway.config;

import com.zhang.seasonsgateway.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

import java.util.LinkedList;

@Configuration
@EnableWebFluxSecurity
public class WebFluxSecurityConfig {
    @Autowired
    private AuthenticationConverter authenticationConverter;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private AuthEntryPointHandler authEntryPointHandler;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private SeasonsAuthenticationManager seasonsAuthenticationManager;

    @Autowired
    private SeasonsAuthorizationManager seasonsAuthorizationManager;

    @Value("${url.white}")
    private String[] excludeAuthPaths;

    @Bean
    public SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) {
        SecurityWebFilterChain chain = http
                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .authenticationSuccessHandler(loginSuccessHandler)  // 登录成功handler
                .authenticationFailureHandler(loginFailureHandler)  // 登陆失败handler
                .authenticationEntryPoint(authEntryPointHandler)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler)           // 无访问权限handler
                .authenticationEntryPoint(authEntryPointHandler)    // 未权限handler
                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)         // 登出成功handler
                .and()
                .authorizeExchange()
                .pathMatchers(excludeAuthPaths).permitAll()         // 白名单放行
                .anyExchange().access(seasonsAuthorizationManager)  // 访问权限控制
                .and()
                .build();

        // 设置自定义登录参数转换器
        chain.getWebFilters()
                .filter(webFilter -> webFilter instanceof AuthenticationWebFilter)
                .subscribe(webFilter -> {
                    AuthenticationWebFilter filter = (AuthenticationWebFilter) webFilter;
                    filter.setServerAuthenticationConverter(authenticationConverter);
                });

        return chain;
    }

    @Bean
    @Primary
    public ReactiveAuthenticationManager reactiveAuthenticationManager() {
        LinkedList<ReactiveAuthenticationManager> managers = new LinkedList<>();
        managers.add(seasonsAuthenticationManager);
        return new DelegatingReactiveAuthenticationManager(managers);
    }
}
