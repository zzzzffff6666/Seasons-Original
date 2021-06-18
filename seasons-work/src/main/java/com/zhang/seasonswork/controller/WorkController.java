package com.zhang.seasonswork.controller;

import com.zhang.seasonswork.model.Work;
import com.zhang.seasonswork.service.WorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class WorkController {
    @Value("${work.default-amount}")
    private int defaultAmount;
    @Value("${work.origin-stored}")
    private String originStoredPath;
    @Value("${work.free-stored}")
    private String freeStoredPath;

    private static final String USER_TAG = "user";
    private static final String ADMIN_TAG = "admin";
    private static final String RESULT_TAG = "result";

    @Autowired
    private WorkService workService;

    // Test
    @GetMapping("/index")
    public Map<String, Object> getIndex() {
        return new HashMap<String, Object>(){{
            put("Hello", "World");
        }};
    }

    @PostMapping("/work/compose")
    public Map<String, Object> compose(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        log.info(request.getHeader("token"));
        int uid = getID(request, USER_TAG);
        Work work = new Work();
        work.setTitle((String) params.get("title"));
        work.setType((String) params.get("type"));
        work.setTag((String) params.get("tag"));
        work.setContent((String) params.get("content"));
        work.setPrice(Float.parseFloat(params.get("price").toString()));
        work.setDaytime(new Timestamp(System.currentTimeMillis()));
        work.setUid(uid);
        if (work.getType() == null) return null;
        Map<String, Object> model = new HashMap<>();
        boolean suc = workService.addWork(work, (MultipartFile) params.get("file"));
        model.put(RESULT_TAG, suc);
        return model;
    }

    @PostMapping("/user/work/delete")
    public Map<String, Object> delete(@RequestParam("wid") int wid, @RequestParam("uid") int uid) {
        Map<String, Object> model = new HashMap<>();
        model.put(RESULT_TAG, workService.deleteWork(wid, uid));

        // 记录日志信息
        log.warn("Delete Work: \nwork id: " + wid + "\nuser id: " + uid);

        return model;
    }

    @PostMapping("/user/work/update")
    public Map<String, Object> updateInfo(@RequestParam Map<String, String> params) {
        int wid = Integer.parseInt(params.get("id"));
        int uid = Integer.parseInt(params.get("uid"));
        String[] info = new String[3];
        info[0] = params.get("title");
        info[1] = params.get("tag");
        info[2] = params.get("content");
        float price = Float.parseFloat(params.get("price"));
        Map<String, Object> model = new HashMap<>();
        model.put(RESULT_TAG, workService.updateInfo(wid, uid, info, price));

        // 记录日志信息
        log.warn("Update Work Info: \nwork id: " + wid + "\nuser id: " + uid);

        return model;
    }

    @PostMapping("/work/report")
    public Map<String, Object> report(@RequestParam("wid") int wid, @RequestParam("uid") int uid) {
        Map<String, Object> model = new HashMap<>();
        model.put(RESULT_TAG, workService.report(wid));

        // 记录日志信息
        log.warn("Report Work: \nwork id: " + wid + "\nuser id: " + uid);

        return model;
    }

    @PostMapping("/work/approve")
    public Map<String, Object> approve(@RequestParam("wid") int wid, @RequestParam("aid") int aid) {
        Map<String, Object> model = new HashMap<>();
        model.put(RESULT_TAG, workService.approve(wid));

        // 记录日志信息
        log.warn("Approve Work: \nwork id: " + wid + "\nadmin id: " + aid);

        return model;
    }

    @PostMapping("/work/disapprove")
    public Map<String, Object> disapprove(@RequestParam("wid") int wid, @RequestParam("aid") int aid) {
        Map<String, Object> model = new HashMap<>();
        model.put(RESULT_TAG, workService.disapprove(wid));

        // 记录日志信息
        log.warn("Disapprove Work: \nwork id: " + wid + "\nadmin id: " + aid);

        return model;
    }

    @PostMapping("/work/laud")
    public Map<String, Object> laudWork(@RequestParam("wid") int wid, @RequestParam("uid") int uid) {
        Map<String, Object> model = new HashMap<>();
        model.put(RESULT_TAG, workService.laudWork(uid, wid));

        // 记录日志信息
        log.warn("Laud Work: \nwork id: " + wid + "\nuser id: " + uid);

        return model;
    }

    @PostMapping("/work/unLaud")
    public Map<String, Object> unLaudWork(@RequestParam("wid") int wid, @RequestParam("uid") int uid) {
        Map<String, Object> model = new HashMap<>();
        model.put(RESULT_TAG, workService.unLaudWork(uid, wid));

        // 记录日志信息
        log.warn("Cancel Laud Work: \nwork id: " + wid + "\nuser id: " + uid);

        return model;
    }

    @PostMapping("/work/store")
    public Map<String, Object> storeWork(@RequestParam("wid") int wid, @RequestParam("uid") int uid) {
        Map<String, Object> model = new HashMap<>();
        model.put(RESULT_TAG, workService.storeWork(uid, wid));

        // 记录日志信息
        log.warn("Store Work: \nwork id: " + wid + "\nuser id: " + uid);

        return model;
    }

    @PostMapping("/work/unStore")
    public Map<String, Object> unStoreWork(@RequestParam("wid") int wid, @RequestParam("uid") int uid) {
        Map<String, Object> model = new HashMap<>();
        model.put(RESULT_TAG, workService.unStoreWork(uid, wid));

        // 记录日志信息
        log.warn("Cancel Store Work: \nwork id: " + wid + "\nuser id: " + uid);

        return model;
    }

    @GetMapping("/work/{uid}/{wid}")
    public Map<String, Object> searchWorkInfo(@PathVariable("uid") int uid, @PathVariable("wid") int wid) {
        Map<String, Object> model = new HashMap<>();
        Work work = workService.searchWorkInfo(wid);
        model.put(RESULT_TAG, work);
        if (work == null) return model;
        model.put("isLaud", workService.isLaud(uid, wid));
        model.put("isStored", workService.isStored(uid, wid));
        return model;
    }

    @GetMapping("/admin/work/{wid}")
    public Map<String, Object> searchApproving(@PathVariable("wid") int wid) {
        Map<String, Object> model = new HashMap<>();
        Work work = workService.searchApprovingWorkInfo(wid);
        model.put(RESULT_TAG, work);
        return model;
    }

    @GetMapping(value = "/work/download/{wid}")
    public ResponseEntity<ByteArrayResource> downloadWork(@PathVariable("wid") int wid, @RequestParam("uid") int uid,
                                                          HttpServletRequest request) throws Exception {
        // 先检查用户是否已购买该作品
        // .......

        // 这里根据我给定的wid来下载指定的文件，
        // 如果你想根据其他方式来下载指定文件的话，请自己修改业务逻辑
        // 1. 根据wid从数据库中获取到指定的文件信息，包括文件名、文件存储地址等等。
        // 1.1 假设我已经获取到了文件信息。
        String type = workService.searchWorkType(wid);
        if (type == null) return null;
        String fileName = wid + type;
        String filePath = originStoredPath + wid + "/" + type;

        // 2. 解决下载的文件的文件名出现中文乱码
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            // IE浏览器
            fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
        } else {
            // 非IE浏览器
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        }

        // 3. 下载文件
        byte[] data = Files.readAllBytes(Paths.get(filePath));
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
                .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(data.length)
                .body(resource);
    }

    @GetMapping(value = {"/user/list/{uid}", "/user/list/{uid}/{page}"})
    public Map<String, Object> searchUserWork(@PathVariable("uid") int uid,
                                              @PathVariable(value = "page", required = false) Integer page) {
        Map<String, Object> model = new HashMap<>();
        if (page == null) page = 0;
        int offset = defaultAmount * page;
        model.put(RESULT_TAG, workService.searchUserWork(uid, offset));
        return model;
    }

    @GetMapping(value = {"/square", "/square/{page}"})
    public Map<String, Object> searchNormalAllWork(@PathVariable(value = "page", required = false) Integer page) {
        Map<String, Object> model = new HashMap<>();
        if (page == null) page = 0;
        int offset = defaultAmount * page;
        model.put(RESULT_TAG, workService.searchNormalAllWork(offset));
        return model;
    }

    @GetMapping(value = {"/admin/all", "/admin/all/{page}"})
    public Map<String, Object> searchAdminAllWork(@PathVariable(value = "page", required = false) Integer page) {
        Map<String, Object> model = new HashMap<>();
        if (page == null) page = 0;
        int offset = defaultAmount * page;
        model.put(RESULT_TAG, workService.searchAdminAllWork(offset));
        return model;
    }

    @GetMapping(value = {"/admin/list", "/admin/list/{page}"})
    public Map<String, Object> searchReportedWork(@PathVariable(value = "page", required = false) Integer page) {
        Map<String, Object> model = new HashMap<>();
        if (page == null) page = 0;
        int offset = defaultAmount * page;
        model.put(RESULT_TAG, workService.searchReportedWork(offset));
        return model;
    }

    @GetMapping(value = {"/square/pattern/{pattern}", "/square/pattern/{pattern}/{page}"})
    public Map<String, Object> searchByPattern(@PathVariable("pattern") String pattern,
                                               @PathVariable(value = "page", required = false) Integer page) {
        Map<String, Object> model = new HashMap<>();
        if (page == null) page = 0;
        int offset = defaultAmount * page;
        List<Work> list = workService.searchByPattern(pattern, offset);
        model.put(RESULT_TAG, list);
        return model;
    }

    @GetMapping(value = {"/square/price/{up}/{down}", "/square/price/{up}/{down}/{page}"})
    public Map<String, Object> searchByPrice(@PathVariable("up") float up, @PathVariable("down") float down,
                                               @PathVariable(value = "page", required = false) Integer page) {
        Map<String, Object> model = new HashMap<>();
        if (page == null) page = 0;
        int offset = defaultAmount * page;
        List<Work> list = workService.searchByPrice(up, down, offset);
        model.put(RESULT_TAG, list);
        return model;
    }

    @GetMapping(value = {"/square/laud", "/square/laud/{page}"})
    public Map<String, Object> searchByLaud(@PathVariable(value = "page", required = false) Integer page) {
        Map<String, Object> model = new HashMap<>();
        if (page == null) page = 0;
        int offset = defaultAmount * page;
        List<Work> list = workService.searchByLaud(offset);
        model.put(RESULT_TAG, list);
        return model;
    }

    @GetMapping(value = {"/square/store", "/square/store/{page}"})
    public Map<String, Object> searchByStore(@PathVariable(value = "page", required = false) Integer page) {
        Map<String, Object> model = new HashMap<>();
        if (page == null) page = 0;
        int offset = defaultAmount * page;
        List<Work> list = workService.searchByStore(offset);
        model.put(RESULT_TAG, list);
        return model;
    }

    public int getID(HttpServletRequest request, String tag) {
        String type = request.getHeader("type");
        int id = Integer.parseInt(request.getHeader("id"));
        return type == null || !type.equals(tag) ? -1 : id;
    }
}
