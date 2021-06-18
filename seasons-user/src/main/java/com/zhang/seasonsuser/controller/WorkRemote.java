package com.zhang.seasonsuser.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient("seasons-work")
public interface WorkRemote {

    @GetMapping("/user/list/{id}/{page}")
    Map<String, Object> getUserWorkList(@RequestParam("id") int uid, @PathVariable("page") int page);

    @GetMapping("/user/store/{id}/{page}")
    Map<String, Object> getUserStoreList(@RequestParam("id") int uid, @PathVariable("page") int page);

    @GetMapping("/user/laud/{id}/{page}")
    Map<String, Object> getUserLaudList(@RequestParam("id") int uid, @PathVariable("page") int page);

    @GetMapping("/work/{uid}/{wid}")
    Map<String, Object> getUserWork(@PathVariable("uid") int uid, @PathVariable("wid") int wid);

    @PostMapping("/user/work/delete")
    Map<String, Object> deleteUserWork(@RequestParam("uid") int uid, @RequestParam("wid") int wid);

    @PostMapping("/user/work/update")
    Map<String, Object> updateUserWork(@RequestParam Map<String, Object> param);

}
