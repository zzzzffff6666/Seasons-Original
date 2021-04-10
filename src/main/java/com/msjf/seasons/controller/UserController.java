package com.msjf.seasons.controller;

import com.msjf.seasons.entity.User;
import com.msjf.seasons.service.LogService;
import com.msjf.seasons.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private LogService logService;

    @PostMapping("/my/info")
    public String modifyInfo(@RequestParam("name") String name, @RequestParam("email") String email,
                             @RequestParam("birthday") String birthday, @RequestParam("space") String space,
                             @RequestParam("sign") String sign, HttpSession session) {
        User u = new User();
        u.setId((int)session.getAttribute("id"));
        u.setName(name);
        u.setEmail(email);
        u.setBirthday(logService.strToDate(birthday));
        u.setSpace(space);
        u.setSign(sign);
        logService.log(name, "User " + name + " has modified information");
        return userService.updateInfo(u) ? "0" : "1";
    }

    @PostMapping("/my/password")
    public String modifyInfo(@RequestParam("old") String oldP, @RequestParam("new") String newP, HttpSession session) {
        int id = (int)session.getAttribute("id");
        User u = userService.select(id);
        if (u.getPassword().equals(oldP)) {
            userService.updatePassword(id, newP);
            logService.log(u.getName(), "User " + u.getName() + " has modified password");
            return "0";
        }
        return "1";
    }

    @GetMapping({"/my/info", "/my"})
    public Map<String, User> getMyInfo(HttpSession session) {
        Map<String, User> result = new HashMap<>();
        result.put("user", userService.select((int)session.getAttribute("id")));
        return result;
    }

}
