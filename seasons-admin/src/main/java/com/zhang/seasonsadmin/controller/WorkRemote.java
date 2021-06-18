package com.zhang.seasonsadmin.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("seasons-work")
public interface WorkRemote {

    @GetMapping("/admin/all/{page}")
    Map<String, Object> getAdminAll(@PathVariable("page") int page);

    @GetMapping("/admin/list/{page}")
    Map<String, Object> getReportedAll(@PathVariable("page") int page);

    @GetMapping("/admin/work/{wid}")
    Map<String, Object> getApproving(@PathVariable("wid") int wid);

    @PostMapping("/work/approve")
    Map<String, Object> approve(@RequestParam("wid") int wid, @RequestParam("aid") int aid);

    @PostMapping("/work/disapprove")
    Map<String, Object> disapprove(@RequestParam("wid") int wid, @RequestParam("aid") int aid);
}
