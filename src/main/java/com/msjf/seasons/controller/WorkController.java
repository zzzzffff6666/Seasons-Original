package com.msjf.seasons.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msjf.seasons.entity.Work;
import com.msjf.seasons.service.LogService;
import com.msjf.seasons.service.WorkService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WorkController {

    @Resource
    private WorkService workService;
    @Resource
    private LogService logService;

    @PostMapping("/compose")
    public String createWork(@RequestParam("work") String json, @RequestParam("file") MultipartFile file,
                             HttpSession session) throws Exception {
        int uid = (int)session.getAttribute("id");
        String name = (String)session.getAttribute("name");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        Work w = new Work();
        w.setTitle(root.findValue("title").asText());
        w.setType(root.findValue("type").asText());
        w.setDaytime(new Timestamp(System.currentTimeMillis()));
        w.setUid(uid);
        w.setContent(root.findValue("content").asText());
        w.setLaud(0);
        w.setStore(0);
        w.setTag(root.findValue("tag").asText());
        w.setState(1);

        int wid = workService.compose(w, file);
        if (wid != 0) {
            logService.log(0, "User " + name + " has create work " + wid);
            return "0";
        }

        return "1";
    }

    @GetMapping({"/square/work", "/my/work", "/check/work"})
    public Map<String, Work> getWorkInfo(@RequestParam("id") int id, HttpSession session) {
        Map<String, Work> result = new HashMap<>();
        if ((int)session.getAttribute("type") == 1) result.put("work", workService.searchByID(id));
        return result;
    }

    @PostMapping("/laud")
    public String laud(@RequestParam("id") int wid, HttpSession session) {
        int uid = (int)session.getAttribute("id");
        return workService.laud(uid, wid) ? "0" : "1";
    }

    @PostMapping("/unLaud")
    public String unLaud(@RequestParam("id") int wid, HttpSession session) {
        int uid = (int)session.getAttribute("id");
        return workService.unLaud(uid, wid) ? "0" : "1";
    }

    @PostMapping("/store")
    public String store(@RequestParam("id") int wid, HttpSession session) {
        int uid = (int)session.getAttribute("id");
        return workService.store(uid, wid) ? "0" : "1";
    }

    @PostMapping("/unStore")
    public String unStore(@RequestParam("id") int wid, HttpSession session) {
        int uid = (int)session.getAttribute("id");
        return workService.unStore(uid, wid) ? "0" : "1";
    }

    @PostMapping("/report")
    public void report(@RequestParam("id") int wid, HttpSession session) {
        String name = (String)session.getAttribute("name");
        workService.report(wid);
        logService.log(0, "User " + name + " has reported work " + wid);
    }

    @PostMapping("/my/work/delete")
    public String deleteWork(@RequestParam("id") int wid, HttpSession session) {
        int uid = (int)session.getAttribute("id");
        String name = (String)session.getAttribute("name");
        if (uid == workService.searchByID(wid).getUid()) {
            workService.delete(wid);
            logService.log(0, "User " + name + " has delete work " + wid);
            return "0";
        }
        return "1";
    }

    @GetMapping("/square/tag")
    public Map<String, List<Work>> searchByTag(@RequestParam("tag") String tag) {
        Map<String, List<Work>> result = new HashMap<>();
        result.put("list", workService.searchByTag(tag));
        return result;
    }

    @GetMapping("/square/title")
    public Map<String, List<Work>> searchByTitle(@RequestParam("title") String title) {
        Map<String, List<Work>> result = new HashMap<>();
        result.put("list", workService.searchByTitle(title));
        return result;
    }

}
