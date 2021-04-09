package com.msjf.seasons.controller;

import com.msjf.seasons.entity.Work;
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

    @GetMapping("/checklist")
    public Map<String, List<Work>> getCheckList(HttpSession session) {
        if ((int)session.getAttribute("type") == 1) {
            Map<String, List<Work>> result = new HashMap<>();
            result.put("list", workService.getReportedWork());
            return result;
        }
        return null;
    }

    @GetMapping("/check/work")
    public Map<String, Work> getWorkInfo(@RequestParam("id") int id, HttpSession session) {
        if ((int)session.getAttribute("type") == 1) {
            Map<String, Work> result = new HashMap<>();
            result.put("list", workService.searchByID(id));
            return result;
        }
        return null;
    }

    @PostMapping("/check")
    public String checkWork(@RequestParam("id") int id, @RequestParam("state") int state, HttpSession session) {
        if ((int)session.getAttribute("type") == 1) {
            workService.check(id, state);
            return "0";
        }
        return "1";
    }

}
