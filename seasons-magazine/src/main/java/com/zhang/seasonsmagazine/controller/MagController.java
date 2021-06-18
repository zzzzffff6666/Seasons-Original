package com.zhang.seasonsmagazine.controller;

import com.zhang.seasonsmagazine.model.Magazine;
import com.zhang.seasonsmagazine.service.MagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MagController {

    private static final String AUTH_TAG = "mag";
    private static final String ID_TAG = "id";
    private static final String TYPE_TAG = "type";
    private static final String RESULT_TAG = "result";
    private static final String ERROR_AUTH_TAG = "没有权限";
    private static final String ERROR_PASSWORD_TAG = "密码错误";

    @Autowired
    private MagService magService;

    @GetMapping("/auth/{param}")
    public Map<String, Object> forAuthentication(@PathVariable("param") String param) {
        Magazine mag = magService.searchForLogin(param);
        Map<String, Object> model = new HashMap<>();
        if (mag == null) {
            model.put("error", "NotFount");
            return model;
        }
        model.put("id", mag.getId());
        model.put("name", mag.getName());
        model.put("password", mag.getPassword());
        model.put("phone", mag.getPhone());
        return model;
    }

    @PostMapping("/mag/register")
    public Map<String, Object> register(@RequestParam Map<String, Object> param) {
        Magazine mag = new Magazine();
        mag.setName(param.get("name").toString());
        mag.setPassword(param.get("password").toString());
        mag.setEmail(param.get("email").toString());
        mag.setPhone(param.get("phone").toString());
        mag.setCompany(param.get("company").toString());
        mag.setLicense(param.get("license").toString());
        mag.setAddress(param.get("address").toString());
        Map<String, Object> model = new HashMap<>();
        model.put(RESULT_TAG, magService.addForRegister(mag));
        model.put("id", mag.getId());
        return model;
    }

    @PostMapping("/mag/info")
    public Map<String, Object> modifyInfo(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        int id = getID(request.getCookies());
        if (id == -1) return error(ERROR_AUTH_TAG);

        Magazine mag = new Magazine();
        mag.setName(param.get("name").toString());
        mag.setEmail(param.get("email").toString());
        mag.setPhone(param.get("phone").toString());
        mag.setCompany(param.get("company").toString());
        mag.setLicense(param.get("license").toString());
        mag.setAddress(param.get("address").toString());
        mag.setId(id);
        Map<String, Object> model = new HashMap<>();
        model.put(RESULT_TAG, magService.changeInfo(mag));
        return model;
    }

    @PostMapping("/mag/password")
    public Map<String, Object> modifyPassword(@RequestParam("old") String oldPassword,
                                              @RequestParam("new") String newPassword,
                                              HttpServletRequest request) {
        int id = getID(request.getCookies());
        if (id == -1) return error(ERROR_AUTH_TAG);

        Map<String, Object> model = new HashMap<>();
        String password = magService.searchForPassword(id);
        if (!oldPassword.equals(password)) return error(ERROR_PASSWORD_TAG);
        model.put(RESULT_TAG, magService.changePassword(id, newPassword));
        return model;
    }

    public Map<String, Object> error(String msg) {
        Map<String, Object> model = new HashMap<>();
        model.put("error", msg);
        model.put(RESULT_TAG, null);
        return model;
    }

    public int getID(Cookie[] cookies) {
        String type = null;
        int id = -1;
        for (Cookie c : cookies) {
            if (c.getName().equals(TYPE_TAG)) type = c.getValue();
            if (c.getName().equals(ID_TAG)) id = Integer.parseInt(c.getValue());
        }
        return type == null || !type.equals(AUTH_TAG) ? -1 : id;
    }
}
