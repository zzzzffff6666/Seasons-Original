package com.msjf.seasons.controller;

import com.msjf.seasons.entity.Work;
import com.msjf.seasons.service.LogService;
import com.msjf.seasons.service.WorkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class OperatorController {

    @Resource
    private WorkService workService;
    @Resource
    private LogService logService;

    @GetMapping("/checklist")
    public Map<String, List<Work>> getCheckList(HttpSession session) {
        Map<String, List<Work>> result = new HashMap<>();
        if ((int)session.getAttribute("type") == 1) result.put("list", workService.getReportedWork());
        return result;
    }

    @PostMapping("/check")
    public String checkWork(@RequestParam("id") int id, @RequestParam("state") int state, HttpSession session) {
        if ((int)session.getAttribute("type") == 1) {
            String name = (String)session.getAttribute("name");
            logService.log(name,
                    "Operator " + name + " has checked work " + id + ", opinion: " + (state == 0 ? "Keep" : "Delete"));
            workService.check(id, state);
            return "0";
        }
        return "1";
    }

}
