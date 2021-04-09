package com.msjf.seasons.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msjf.seasons.entity.Work;
import com.msjf.seasons.service.WorkService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.sql.Timestamp;

@RestController
public class WorkController {

    @Resource
    private WorkService workService;

    @PostMapping("/compose")
    public String createWork(@RequestParam("work") String json, @RequestParam("file") MultipartFile file) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        Work w = new Work();
        w.setTitle(root.findValue("title").asText());
        w.setType(root.findValue("type").asText());
        w.setDaytime(new Timestamp(System.currentTimeMillis()));
        w.setUid(root.findValue("uid").asInt());
        w.setContent(root.findValue("content").asText());
        w.setLaud(0);
        w.setStore(0);
        w.setTag(root.findValue("tag").asText());
        w.setState(1);

        return workService.compose(w, file) != 0 ? "0" : "1";
    }
}
