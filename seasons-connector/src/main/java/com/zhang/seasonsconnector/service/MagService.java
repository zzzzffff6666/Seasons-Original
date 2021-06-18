package com.zhang.seasonsconnector.service;

import com.zhang.seasonsconnector.mapper.MagMapper;
import com.zhang.seasonsconnector.model.Magazine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MagService {

    @Resource
    private MagMapper magMapper;

    public Magazine searchForLogin(String param) {
        int len = param.length();
        if (len == 11) {
            return magMapper.selectByPhone(param);
        } else if (len > 11) {
            return magMapper.selectByEmail(param);
        } else if (len > 0){
            return magMapper.selectByName(param);
        } else {
            return null;
        }
    }

    public Magazine searchForInfo(int id) {
        return magMapper.selectByID(id);
    }

    public int addForRegister(Magazine mag) {
        return magMapper.insert(mag);
    }

    public int remove(int id) {
        return magMapper.delete(id);
    }

    public int changeInfo(Magazine mag) {
        return magMapper.updateInfo(mag);
    }

    public int changePassword(int id, String password) {
        return magMapper.updatePassword(id, password);
    }

    public void lockAccount(int id) {
        magMapper.updateLocked(id, 1);
    }

    public void unlockAccount(int id) {
        magMapper.updateLocked(id, 0);
    }
}
