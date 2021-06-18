package com.zhang.seasonsadmin.service;

import com.zhang.seasonsadmin.mapper.AdminMapper;
import com.zhang.seasonsadmin.model.Admin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AdminService {
    @Resource
    private AdminMapper adminMapper;

    public Admin searchForLogin(String param) {
        int len = param.length();
        if (len == 11) {
            return adminMapper.selectByPhone(param);
        } else if (len > 11) {
            return adminMapper.selectByEmail(param);
        } else if (len > 0){
            return adminMapper.selectByName(param);
        } else {
            return null;
        }
    }

    public Admin searchForInfo(int id) {
        return adminMapper.selectByID(id);
    }

    public String searchForPassword(int id) {
        return adminMapper.selectPasswordByID(id);
    }

    public int addForRegister(Admin admin) {
        return adminMapper.insert(admin);
    }

    public int remove(int id) {
        return adminMapper.delete(id);
    }

    public int changeInfo(Admin admin) {
        return adminMapper.updateInfo(admin);
    }

    public int changePassword(int id, String password) {
        return adminMapper.updatePassword(id, password);
    }

    public void lockAccount(int id) {
        adminMapper.updateLocked(id, 1);
    }

    public void unlockAccount(int id) {
        adminMapper.updateLocked(id, 0);
    }
}
