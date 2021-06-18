package com.zhang.seasonsconnector.config;

import com.zhang.seasonsconnector.service.SeasonsUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private TokenAuthenticationFilter tokenAuthenticationFilter;
    @Autowired
    private SeasonsAuthenticationEntryPoint seasonsAuthenticationEntryPoint;
    @Autowired
    private SeasonsAccessDeniedHandler seasonsAccessDeniedHandler;
    @Autowired
    private SeasonsUserDetailsService seasonsUserDetailsService;
    @Autowired
    private LoginProvider loginProvider;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        return new LoginFilter(authenticationManager());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login", "/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .logout()
                .logoutSuccessUrl("/login");

        http.addFilterBefore(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(tokenAuthenticationFilter, LoginFilter.class);

        http
                .exceptionHandling()
                .accessDeniedHandler(seasonsAccessDeniedHandler)
                .authenticationEntryPoint(seasonsAuthenticationEntryPoint);
    }

    /**
     * 密码加密
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(loginProvider);
        auth.userDetailsService(seasonsUserDetailsService);
    }

    /**
     * 静态资源过滤
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET, "/static/**","/*.html","/css/**","/img/**","/js/**","/login","/register");
    }
}
