package com.msjf.seasons.controller;

import com.msjf.seasons.entity.Admin;
import com.msjf.seasons.entity.Magazine;
import com.msjf.seasons.entity.User;
import com.msjf.seasons.service.ConnectService;
import com.msjf.seasons.service.LogService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 登陆后会在HttpSession中存储两个属性
 * type 0表示用户，1表示管理员，2表示杂志社
 * id 用户或管理员的ID
 */
@RestController
public class ConnectController {

    @Resource
    private ConnectService connectService;
    @Resource
    private LogService logService;

    @PostMapping("/login")
    public String userLogin(@RequestParam("param") String param, @RequestParam("password") String password, HttpSession session) {
        User u = connectService.userSearch(param);
        if (u != null && u.getPassword().equals(password)) {
            session.setAttribute("type", 0);
            session.setAttribute("id", u.getId());
            session.setAttribute("name", u.getName());
            return "0";
        }
        return "1";
    }

    @PostMapping("/adLogin")
    public String opLogin(@RequestParam("name") String name, @RequestParam("password") String password, HttpSession session) {
        Admin op = connectService.opSearch(name);
        if (op != null && op.getPassword().equals(password)) {
            session.setAttribute("type", 1);
            session.setAttribute("id", op.getId());
            session.setAttribute("name", op.getName());
            return "0";
        }
        return "1";
    }

    @PostMapping("/magLogin")
    public String magLogin(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
        Magazine mag = connectService.magSearch(email);
        if (mag != null && mag.getPassword().equals(password)) {
            session.setAttribute("type", 2);
            session.setAttribute("id", mag.getId());
            session.setAttribute("name", mag.getName());
            return "0";
        }
        return "1";
    }

    @PostMapping("/register")
    public String userRegister(@RequestParam("name") String name, @RequestParam("password") String password,
                               @RequestParam("email") String email) {
        if (connectService.userRegister(name, password, email)) {
            logService.log(0, "User " + name + " has already registered");
            return "0";
        }
        return "1";
    }

    @PostMapping("/magRegister")
    public String magRegister(@RequestParam("name") String name, @RequestParam("password") String password,
                           @RequestParam("license") String license, @RequestParam("address") String address,
                           @RequestParam("email") String email, @RequestParam("phone") String phone,
                           HttpSession session) {
        Magazine mag = new Magazine();
        mag.setName(name);
        mag.setPassword(password);
        mag.setLicense(license);
        mag.setAddress(address);
        mag.setEmail(email);
        mag.setPhone(phone);

        //验证信息真实性

        return connectService.magRegister(mag) ? "0" : "1";
    }
}
