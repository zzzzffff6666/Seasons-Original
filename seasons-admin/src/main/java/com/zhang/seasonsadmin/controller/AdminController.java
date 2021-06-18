package com.zhang.seasonsadmin.controller;

import com.zhang.seasonsadmin.model.Admin;
import com.zhang.seasonsadmin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AdminController {

    private static final String AUTH_TAG = "admin";
    private static final String ID_TAG = "id";
    private static final String TYPE_TAG = "type";
    private static final String RESULT_TAG = "result";
    private static final String ERROR_AUTH_TAG = "没有权限";
    private static final String ERROR_PASSWORD_TAG = "密码错误";

    @Autowired
    private AdminService adminService;

    @Autowired
    private WorkRemote workRemote;

    @GetMapping("/auth/{param}")
    public Map<String, Object> forAuthentication(@PathVariable("param") String param) {
        Admin admin = adminService.searchForLogin(param);
        Map<String, Object> model = new HashMap<>();
        if (admin == null) {
            model.put("error", "NotFount");
            return model;
        }
        model.put("id", admin.getId());
        model.put("name", admin.getName());
        model.put("password", admin.getPassword());
        model.put("phone", admin.getPhone());
        return model;
    }

    @GetMapping({"/admin/all", "/admin/all/{page}"})
    public Map<String, Object> getAllWorkList(@PathVariable(value = "page", required = false) Integer page,
                                              HttpServletRequest request) {
        int id = getID(request.getCookies());
        if (id == -1) return error(ERROR_AUTH_TAG);

        return workRemote.getAdminAll(page == null ? 0 : page);
    }

    @GetMapping({"/admin/list", "/admin/list/{page}"})
    public Map<String, Object> getReportedWorkList(@PathVariable(value = "page", required = false) Integer page,
                                                   HttpServletRequest request) {
        int id = getID(request.getCookies());
        if (id == -1) return error(ERROR_AUTH_TAG);

        return workRemote.getReportedAll(page == null ? 0 : page);
    }

    @GetMapping("/admin/work/{wid}")
    public Map<String, Object> getReportedWork(@PathVariable("wid") Integer wid, HttpServletRequest request) {
        int id = getID(request.getCookies());
        if (id == -1) return error(ERROR_AUTH_TAG);

        return workRemote.getApproving(wid);
    }

    @PostMapping("/admin/approve")
    public Map<String, Object> approve(@RequestParam("id") int wid, HttpServletRequest request) {
        int id = getID(request.getCookies());
        if (id == -1) return error(ERROR_AUTH_TAG);

        return workRemote.approve(wid, id);
    }

    @PostMapping("/admin/disapprove")
    public Map<String, Object> disapprove(@RequestParam("id") int wid, HttpServletRequest request) {
        int id = getID(request.getCookies());
        if (id == -1) return error(ERROR_AUTH_TAG);

        return workRemote.disapprove(wid, id);
    }

    @PostMapping("/admin/password")
    public Map<String, Object> modifyPassword(@RequestParam("old") String oldPassword,
                                              @RequestParam("new") String newPassword,
                                              HttpServletRequest request) {
        int id = getID(request.getCookies());
        if (id == -1) return error(ERROR_AUTH_TAG);

        Map<String, Object> model = new HashMap<>();
        String password = adminService.searchForPassword(id);
        if (!oldPassword.equals(password)) return error(ERROR_PASSWORD_TAG);
        model.put(RESULT_TAG, adminService.changePassword(id, newPassword));
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
