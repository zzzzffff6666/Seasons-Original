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
     * @param uname 用户名
     * @param content 具体内容
     * @return 是否成功
     */
    public boolean log(String uname, String content) {
        Log log = new Log();
        log.setUname(uname);
        log.setDaytime(new Timestamp(System.currentTimeMillis()));
        log.setContent(content);
        return logMapper.insert(log) == 1;
    }

    /**
     * 获取关于某一用户的日志
     * @param uname 用户名
     */
    public void printUserLog(String uname) {
        List<Log> logs = logMapper.select(uname);
        System.out.println("********** Print Start **********");
        for (Log l : logs) {
            System.out.println(l);
        }
        System.out.println("********** Print End **********");
    }

    /**
     * 获取某一时间段的日志
     * @param begin 开始时间
     * @param end 结束时间
     */
    public void printTimeLog(Timestamp begin, Timestamp end) {
        List<Log> logs = logMapper.selectByTime(begin, end);
        System.out.println("********** Print Start **********");
        for (Log l : logs) {
            System.out.println(l);
        }
        System.out.println("********** Print End **********");
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
