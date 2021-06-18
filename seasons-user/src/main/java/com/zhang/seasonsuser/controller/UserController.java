package com.zhang.seasonsuser.controller;

import com.zhang.seasonsuser.model.User;
import com.zhang.seasonsuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private static final String AUTH_TAG = "user";
    private static final String ID_TAG = "id";
    private static final String TYPE_TAG = "type";
    private static final String RESULT_TAG = "result";
    private static final String ERROR_AUTH_TAG = "没有权限";
    private static final String ERROR_PASSWORD_TAG = "密码错误";
    private static final String ERROR_MONEY_TAG = "余额不足";

    @Autowired
    private WorkRemote workRemote;

    @Autowired
    private UserService userService;

    @GetMapping("/auth/{param}")
    public Map<String, Object> forAuthentication(@PathVariable("param") String param) {
        User user = userService.searchForLogin(param);
        Map<String, Object> model = new HashMap<>();
        if (user == null) {
            model.put("error", "NotFount");
            return model;
        }
        model.put("id", user.getId());
        model.put("name", user.getName());
        model.put("password", user.getPassword());
        model.put("phone", user.getPhone());
        return model;
    }

    @PostMapping("/user/register")
    public Map<String, Object> register(@RequestParam Map<String, Object> param) {
        User user = new User();
        user.setName(param.get("name").toString());
        user.setPassword(param.get("password").toString());
        user.setEmail(param.get("email").toString());
        user.setPhone(param.get("phone").toString());
        user.setBirthday(strToDate(param.get("birthday").toString()));
        user.setSign(param.get("sign").toString());
        Map<String, Object> model = new HashMap<>();
        model.put(RESULT_TAG, userService.addForRegister(user));
        model.put("id", user.getId());
        return model;
    }

    @PostMapping("/user/info")
    public Map<String, Object> modifyInfo(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        int id = getID(request);
        if (id == -1) return error(ERROR_AUTH_TAG);

        User user = new User();
        user.setName(param.get("name").toString());
        user.setEmail(param.get("email").toString());
        user.setPhone(param.get("phone").toString());
        user.setBirthday(strToDate(param.get("birthday").toString()));
        user.setSign(param.get("sign").toString());
        user.setId(id);
        Map<String, Object> model = new HashMap<>();
        model.put(RESULT_TAG, userService.changeInfo(user));
        return model;
    }

    @PostMapping("/user/password")
    public Map<String, Object> modifyPassword(@RequestParam("old") String oldPassword,
                                              @RequestParam("new") String newPassword,
                                              HttpServletRequest request) {
        int id = getID(request);
        if (id == -1) return error(ERROR_AUTH_TAG);

        Map<String, Object> model = new HashMap<>();
        String password = userService.searchForPassword(id);
        if (!oldPassword.equals(password)) return error(ERROR_PASSWORD_TAG);
        model.put(RESULT_TAG, userService.changePassword(id, newPassword));
        return model;
    }

    @PostMapping("/user/deposit")
    public Map<String, Object> deposit(@RequestParam("amount") float amount, HttpServletRequest request) {
        int id = getID(request);
        if (id == -1) return error(ERROR_AUTH_TAG);

        Map<String, Object> model = new HashMap<>();
        model.put(RESULT_TAG, userService.changeCoin(id, amount));
        return model;
    }

    @PostMapping("/user/buy")
    public Map<String, Object> buyWork(@RequestParam("wid") int wid, @RequestParam("price") float price, HttpServletRequest request) {
        int id = getID(request);
        if (id == -1) return error(ERROR_AUTH_TAG);

        float remain = userService.searchForCoin(id);
        if (price > remain) return error(ERROR_MONEY_TAG);
        Map<String, Object> model = new HashMap<>();

        // 将购买信息插入 BUY 表中

        model.put(RESULT_TAG, userService.changeCoin(id, -price));
        return model;
    }

    @GetMapping({"/user", "/user/info"})
    public Map<String, Object> getUserInfo(HttpServletRequest request) {
        int id = getID(request);
        if (id == -1) return error(ERROR_AUTH_TAG);

        Map<String, Object> model = new HashMap<>();
        model.put(RESULT_TAG, userService.searchForInfo(id));
        return model;
    }

    @GetMapping({"/user/list", "/user/list/{page}"})
    public Map<String, Object> getWorkList(@PathVariable(value = "page", required = false) Integer page, HttpServletRequest request) {
        int id = getID(request);
        if (id == -1) return error(ERROR_AUTH_TAG);

        return workRemote.getUserWorkList(id, page == null ? 0 : page);
    }

    @GetMapping({"/user/store", "/user/store/{page}"})
    public Map<String, Object> getStoreList(@PathVariable(value = "page", required = false) Integer page, HttpServletRequest request) {
        int id = getID(request);
        if (id == -1) return error(ERROR_AUTH_TAG);

        return workRemote.getUserStoreList(id, page == null ? 0 : page);
    }

    @GetMapping({"/user/laud", "/user/laud/{page}"})
    public Map<String, Object> getLaudList(@PathVariable(value = "page", required = false) Integer page, HttpServletRequest request) {
        int id = getID(request);
        if (id == -1) return error(ERROR_AUTH_TAG);

        return workRemote.getUserLaudList(id, page == null ? 0 : page);
    }

    @GetMapping("/user/work/{wid}")
    public Map<String, Object> getWork(@PathVariable("wid") int wid, HttpServletRequest request) {
        int id = getID(request);
        if (id == -1) return error(ERROR_AUTH_TAG);

        return workRemote.getUserWork(id, wid);
    }

    @PostMapping("/user/work/delete")
    public Map<String, Object> deleteWork(@RequestParam("id") int wid, HttpServletRequest request) {
        int id = getID(request);
        if (id == -1) return error(ERROR_AUTH_TAG);

        return workRemote.deleteUserWork(id, wid);
    }

    @PostMapping("/user/work/update")
    public Map<String, Object> updateWork(@RequestParam Map<String, Object> param, HttpServletRequest request) {
        int id = getID(request);
        if (id == -1) return error(ERROR_AUTH_TAG);

        param.put("uid", id);
        return workRemote.updateUserWork(param);
    }

    public Map<String, Object> error(String msg) {
        Map<String, Object> model = new HashMap<>();
        model.put("error", msg);
        model.put(RESULT_TAG, null);
        return model;
    }

    public int getID(HttpServletRequest request) {
        String type = request.getHeader(TYPE_TAG);
        int id = Integer.parseInt(request.getHeader(ID_TAG));
        return type == null || !type.equals(AUTH_TAG) ? -1 : id;
    }

    public Date strToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = null;
        try {
            d = format.parse(str);
        } catch (Exception e) {
            return null;
        }
        return new Date(d.getTime());
    }
}
