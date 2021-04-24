package com.msjf.seasons.service;

import com.msjf.seasons.entity.Log;
import com.msjf.seasons.mapper.LogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class LogService {

    @Resource
    private LogMapper logMapper;

    /**
     * 记录日志
     * @param type 事件类别：用户，管理员，杂志社
     * @param content 具体内容
     */
    public void log(int type, String content) {
        Log log = new Log();
        log.setType(type);
        log.setDaytime(new Timestamp(System.currentTimeMillis()));
        log.setContent(content);
        logMapper.insert(log);
    }

    /**
     * 获取关于某一类别的日志
     * @param type 日志类别
     * @return 日志列表
     */
    public List<Log> printUserLog(int type) {
        return logMapper.selectByType(type);
    }

    /**
     * 获取某一时间段的日志
     * @param begin 开始时间
     * @param end 结束时间
     * @return 日志列表
     */
    public List<Log> printTimeLog(Timestamp begin, Timestamp end) {
        return logMapper.selectByTime(begin, end);
    }

    /**
     * 字符串转日期
     * @param str 时间字符串
     * @return 日期对象
     */
    public Date strToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date d = null;
        try {
            d = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (d != null) {
            return new Date(d.getTime());
        }
        return null;
    }

}
