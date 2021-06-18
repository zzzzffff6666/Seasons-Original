package com.zhang.seasonsconnector.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ConnectController {

    @GetMapping("/index")
    public String getIndex() {
        return "/index";
    }

    @GetMapping("/login")
    public String toLogin() {
        return "/login"; // classpath: /templates/login.html
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "/index";
    }
}
