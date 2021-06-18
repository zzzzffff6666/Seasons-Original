package com.zhang.seasonswork.service;

import com.zhang.seasonswork.mapper.LaudMapper;
import com.zhang.seasonswork.mapper.StoreMapper;
import com.zhang.seasonswork.mapper.WorkMapper;
import com.zhang.seasonswork.model.Work;
import com.zhang.seasonswork.utils.WaterMarkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

@Service
@Slf4j
public class WorkService {

    @Value("${work.default-amount}")
    private int defaultAmount;
    @Value("${work.origin-stored}")
    private String originStoredPath;
    @Value("${work.free-stored}")
    private String freeStoredPath;
    @Value("${work.prefix}")
    private String prefix;

    @Resource
    private WorkMapper workMapper;
    @Resource
    private LaudMapper laudMapper;
    @Resource
    private StoreMapper storeMapper;

    public boolean saveFile(MultipartFile file, String originFilePath, String freeFilePath) {
        try {
            ClassPathResource originResource = new ClassPathResource(originFilePath);
            File originFile = originResource.getFile();
            if (!originFile.exists()) originFile.createNewFile();
            BufferedOutputStream out1 = new BufferedOutputStream(new FileOutputStream(originFile));
            out1.write(file.getBytes());
            out1.flush();
            out1.close();
            ClassPathResource freeResource = new ClassPathResource(freeFilePath);
            File freeFile = freeResource.getFile();
            if (!freeFile.exists()) freeFile.createNewFile();
            WaterMarkUtils.mark(originFile, freeFile);

        } catch (IOException e) {
            e.printStackTrace();
            log.info("图片保存失败！");
            return false;
        }
        return true;
    }

    /**
     * 添加作品
     * @param work 作品信息
     * @param file 文件
     * @return 是否成功
     */
    public boolean addWork(Work work, MultipartFile file) {
        if (file.isEmpty()) return false;
        int n = workMapper.insert(work);
        workMapper.updateUrl(prefix + work.getId() + work.getType(), work.getId());
        if (n <= 0 || work.getId() < 0) return false;
        //图片命名
        String originFilePath = originStoredPath + work.getId() + work.getType();
        String freeFilePath = freeStoredPath + work.getId() + work.getType();
        if (!saveFile(file, originFilePath, freeFilePath)) {
            workMapper.delete(work.getId(), work.getUid());
            return false;
        }
        return true;
    }

    /**
     * 删除作品
     * @param wid 作品ID
     * @param uid 用户ID
     * @return 是否成功
     */
    public boolean deleteWork(int wid, int uid) {
        return workMapper.delete(wid, uid) == 1;
    }

    /**
     * 更新作品信息
     * @param wid 作品ID
     * @param uid 用户ID
     * @param info 作品新信息数组
     *             0->作品标题
     *             1->作品标签
     *             2->作品附文
     * @param price 新价格
     * @return 是否成功
     */
    public boolean updateInfo(int wid, int uid, String[] info, float price) {
        return workMapper.updateInfo(wid, uid, info[0], info[1], info[2], price) == 1;
    }

    /**
     * 举报作品包含非法内容
     * @param wid 作品ID
     * @return 是否成功
     */
    public boolean report(int wid) {
        return workMapper.updateState(wid, 1) == 1;
    }

    /**
     * 作品审核通过
     * @param wid 作品ID
     * @return 是否成功
     */
    public boolean approve(int wid) {
        return workMapper.updateState(wid, 0) == 0;
    }

    /**
     * 作品审核不通过，删除
     * @param wid 作品ID
     * @return 是否成功
     */
    public boolean disapprove(int wid) {
        return workMapper.deleteByAdmin(wid) == 1;
    }

    /**
     * 判断是否已经点赞
     * @param uid 用户ID
     * @param wid 作品ID
     * @return 是否点赞
     */
    public boolean isLaud(int uid, int wid) {
        return laudMapper.select(uid, wid) == 1;
    }

    /**
     * 点赞
     * @param wid 作品ID
     * @return 是否成功
     */
    public boolean laudWork(int uid, int wid) {
        int suc = laudMapper.insert(uid, wid);
        return workMapper.updateLaud(wid, suc) == 1;
    }

    /**
     * 取消点赞
     * @param wid 作品ID
     * @return 是否成功
     */
    public boolean unLaudWork(int uid, int wid) {
        int suc = laudMapper.delete(uid, wid);
        return workMapper.updateLaud(wid, -suc) == 1;
    }

    /**
     * 判断是否已经收藏
     * @param uid 用户ID
     * @param wid 作品ID
     * @return 是否收藏
     */
    public boolean isStored(int uid, int wid) {
        return storeMapper.select(uid, wid) == 1;
    }

    /**
     * 收藏作品
     * @param wid 作品ID
     * @return 是否成功
     */
    public boolean storeWork(int uid, int wid) {
        int suc = storeMapper.insert(uid, wid);
        return workMapper.updateStore(wid, suc) == 1;
    }

    /**
     * 取消收藏作品
     * @param wid 作品ID
     * @return 是否成功
     */
    public boolean unStoreWork(int uid, int wid) {
        int suc = storeMapper.delete(uid, wid);
        return workMapper.updateStore(wid, -suc) == 1;
    }

    /**
     * 查询作品的文件类型
     * @param wid 作品ID
     * @return 作品类型
     */
    public String searchWorkType(int wid) {
        return workMapper.selectForType(wid);
    }

    /**
     * 查询未被举报作品的信息
     * @param wid 作品ID
     * @return 查询结果
     */
    public Work searchWorkInfo(int wid) {
        return workMapper.selectByID(wid);
    }

    /**
     * 查询任意作品的信息
     * @param wid 作品ID
     * @return 查询结果
     */
    public Work searchApprovingWorkInfo(int wid) {
        return workMapper.selectApprovingByID(wid);
    }

    /**
     * 查询用户创作的作品
     * @param uid 用户ID
     * @param offset 偏移量
     * @return 查询结果
     */
    public List<Work> searchUserWork(int uid, int offset) {
        return workMapper.selectByUid(uid, offset, defaultAmount);
    }

    /**
     * 查询默认的作品
     * @param offset 偏移量
     * @return 查询结果
     */
    public List<Work> searchNormalAllWork(int offset) {
        return workMapper.selectAll(offset, defaultAmount);
    }

    /**
     * 查询所有的作品
     * @param offset 偏移量
     * @return 查询结果
     */
    public List<Work> searchAdminAllWork(int offset) {
        return workMapper.selectAdminAll(offset, defaultAmount);
    }

    /**
     * 查询被举报的作品
     * @param offset 偏移量
     * @return 查询结果
     */
    public List<Work> searchReportedWork(int offset) {
        return workMapper.selectByApproved(offset, defaultAmount);
    }

    /**
     * 查询符合模板的作品
     * @param pattern 模板
     * @param offset 偏移量
     * @return 查询结果
     */
    public List<Work> searchByPattern(String pattern, int offset) {
        return workMapper.selectByPattern(pattern, offset, defaultAmount);
    }

    /**
     * 查询在价格区间内的作品
     * @param up 上边界
     * @param down 下边界
     * @param offset 偏移量
     * @return 查询结果
     */
    public List<Work> searchByPrice(float up, float down, int offset) {
        return workMapper.selectByPrice(up, down, offset, defaultAmount);
    }

    /**
     * 按点赞量降序查询作品
     * @param offset 偏移量
     * @return 查询结果
     */
    public List<Work> searchByLaud(int offset) {
        return workMapper.selectByLaud(offset, defaultAmount);
    }

    /**
     * 按收藏量降序查询作品
     * @param offset 偏移量
     * @return 查询结果
     */
    public List<Work> searchByStore(int offset) {
        return workMapper.selectByStore(offset, defaultAmount);
    }

}
