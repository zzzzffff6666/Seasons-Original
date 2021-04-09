package com.msjf.seasons.service;

import com.msjf.seasons.entity.Log;
import com.msjf.seasons.mapper.LogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service
public class LogService {

    @Resource
    private LogMapper logMapper;

    public boolean log(String uname, Timestamp daytime, String content) {
        Log log = new Log();
        log.setUname(uname);
        log.setDaytime(daytime);
        log.setContent(content);
        return logMapper.insert(log) == 1;
    }

    public void printUserLog(String uname) {
        List<Log> logs = logMapper.select(uname);
        System.out.println("********** Print Start **********");
        for (Log l : logs) {
            System.out.println(l);
        }
        System.out.println("********** Print End **********");
    }

    public void printTimeLog(Timestamp begin, Timestamp end) {
        List<Log> logs = logMapper.selectByTime(begin, end);
        System.out.println("********** Print Start **********");
        for (Log l : logs) {
            System.out.println(l);
        }
        System.out.println("********** Print End **********");
    }
}
