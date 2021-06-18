package com.zhang.seasonsconnector.service;

import com.zhang.seasonsconnector.mapper.UserMapper;
import com.zhang.seasonsconnector.model.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public User searchForLogin(String param) {
        int len = param.length();
        if (len == 11) {
            return userMapper.selectByPhone(param);
        } else if (len > 11) {
            return userMapper.selectByEmail(param);
        } else if (len > 0){
            return userMapper.selectByName(param);
        } else {
            return null;
        }
    }

    public User searchForInfo(int id) {
        return userMapper.selectByID(id);
    }

    public int addForRegister(User user) {
        return userMapper.insert(user);
    }

    public int remove(int id) {
        return userMapper.delete(id);
    }

    public int changeInfo(User user) {
        return userMapper.updateInfo(user);
    }

    public int changePassword(int id, String password) {
        return userMapper.updatePassword(id, password);
    }

    public void lockAccount(int id) {
        userMapper.updateLocked(id, 1);
    }

    public void unlockAccount(int id) {
        userMapper.updateLocked(id, 0);
    }

    public User addByPhone(String phone) {
        User user = userMapper.selectByPhone(phone);
        if (user == null) {
            user = new User();
            user.setPhone(phone);
            user.setName("P" + phone);
            userMapper.insert(user);
            user = userMapper.selectByPhone(phone);
        }
        return user;
    }
}
