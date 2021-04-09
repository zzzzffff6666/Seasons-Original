package com.msjf.seasons.controller;

import com.msjf.seasons.entity.Operator;
import com.msjf.seasons.entity.User;
import com.msjf.seasons.service.ConnectService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 登陆后会在HttpSession中存储两个属性
 * @Param type 0表示用户，1表示管理员
 * @Param id 用户或管理员的ID
 */
@RestController
public class ConnectController {

    @Resource
    private ConnectService connectService;

    @PostMapping("/login")
    public String userLogin(@RequestParam("name") String name, @RequestParam("password") String password, HttpSession session) {
        User u = connectService.userSearch(name);
        if (u != null && u.getPassword().equals(password)) {
            session.setAttribute("type", 0);
            session.setAttribute("id", u.getId());
            return "0";
        }
        return "1";
    }

    @PostMapping("/oplogin")
    public String opLogin(@RequestParam("name") String name, @RequestParam("password") String password, HttpSession session) {
        Operator op = connectService.opSearch(name);
        if (op != null && op.getPassword().equals(password)) {
            session.setAttribute("type", 1);
            session.setAttribute("id", op.getId());
            return "0";
        }
        return "1";
    }

    @PostMapping("/register")
    public String userRegister(@RequestParam("name") String name, @RequestParam("password") String password,
                               @RequestParam("email") String email) {
        return connectService.register(name, password, email) ? "0" : "1";
    }

}
