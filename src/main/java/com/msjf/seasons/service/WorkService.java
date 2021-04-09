package com.msjf.seasons.service;

import com.msjf.seasons.entity.Work;
import com.msjf.seasons.mapper.LaudMapper;
import com.msjf.seasons.mapper.StoreMapper;
import com.msjf.seasons.mapper.WorkMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class WorkService {
    @Resource
    private WorkMapper workMapper;
    @Resource
    private StoreMapper storeMapper;
    @Resource
    private LaudMapper laudMapper;

    /**
     * 获取被举报的作品
     * @return 作品列表
     */
    public List<Work> getReportedWork() {
        return workMapper.searchByState(1);
    }

    /**
     * 审批被举报的作品
     * @param id 作品ID
     * @param state 审批意见
     */
    public void check(int id, int state) {
        if (state == 0) workMapper.updateState(id, state);
        else workMapper.deleteByID(id);
    }

    /**
     * 点赞状态
     * @param uid 用户ID
     * @param wid 作品ID
     * @return 是否已赞
     */
    public boolean isLaud(int uid, int wid) {
        return laudMapper.select(uid, wid) == 1;
    }

    /**
     * 点赞
     * @param uid 用户ID
     * @param wid 作品ID
     * @return 是否成功
     */
    public boolean laud(int uid, int wid) {
        if (laudMapper.add(uid, wid) == 1) {
            workMapper.updateLaud(wid, 1);
            return true;
        }
        return false;
    }

    /**
     * 取消点赞
     * @param uid 用户ID
     * @param wid 作品ID
     * @return 是否成功
     */
    public boolean unLaud(int uid, int wid) {
        if (laudMapper.delete(uid, wid) == 1) {
            workMapper.updateLaud(wid, -1);
            return true;
        }
        return false;
    }

    /**
     * 收藏状态
     * @param uid 用户ID
     * @param wid 作品ID
     * @return 是否已收藏
     */
    public boolean isStore(int uid, int wid) {
        return storeMapper.select(uid, wid) == 1;
    }

    /**
     * 收藏
     * @param uid 用户ID
     * @param wid 作品ID
     * @return 是否成功
     */
    public boolean store(int uid, int wid) {
        if (storeMapper.add(uid, wid) == 1) {
            workMapper.updateStore(wid, 1);
            return true;
        }
        return false;
    }

    /**
     * 取消收藏
     * @param uid 用户ID
     * @param wid 作品ID
     * @return 是否成功
     */
    public boolean unStore(int uid, int wid) {
        if (storeMapper.delete(uid, wid) == 1) {
            workMapper.updateStore(wid, -1);
            return true;
        }
        return false;
    }

    /**
     * 创作
     * @param work 作品信息
     * @param file 文件
     * @return 作品ID
     */
    public int compose(Work work, MultipartFile file) {
        int i = workMapper.add(work);
        String path = "";
        try {
            path = ResourceUtils.getURL("classpath:").getPath() + "static/photo/";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Path filePath = Paths.get(path, i + work.getType());
        byte[] fileBytes;
        try {
            fileBytes = file.getBytes();
            Files.write(filePath, fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 删除作品
     * @param id 作品ID
     * @return 是否成功
     */
    public boolean delete(int id) {
        return workMapper.deleteByID(id) == 1;
    }

    /**
     * 修改作品信息
     * @param id 作品ID
     * @param content 作品内容
     * @param tag 作品标签
     * @param title 作品标题
     */
    public void update(int id, String content, String tag, String title) {
        workMapper.updateContent(id, content);
        workMapper.updateTag(id, tag);
        workMapper.updateTitle(id, title);
    }

    /**
     * 获取所有作品
     * @return 作品列表
     */
    public List<Work> getAllWork() {
        return workMapper.searchAll();
    }

    /**
     * 获取用户的作品
     * @param uid 用户ID
     * @return 作品列表
     */
    public List<Work> getUserWork(int uid) {
        return workMapper.searchByUID(uid);
    }

    /**
     * 通过标签搜索作品
     * @param tag 作品标签
     * @return 作品列表
     */
    public List<Work> searchByTag(String tag) {
        return workMapper.searchByTag(tag);
    }

    /**
     * 通过标题搜索作品
     * @param title 标题
     * @return 作品列表
     */
    public List<Work> searchByTitle(String title) {
        return workMapper.searchByTitle(title);
    }
}
