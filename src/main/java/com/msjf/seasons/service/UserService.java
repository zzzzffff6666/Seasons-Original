package com.msjf.seasons.service;

import com.msjf.seasons.entity.User;
import com.msjf.seasons.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 修改个人信息
     * @param user 新的用户信息
     * @return 是否成功
     */
    public boolean updateInfo(User user) {
        return userMapper.updateInfo(user) == 1;
    }

    /**
     * 修改密码
     * @param id 用户ID
     * @param oldP 旧密码
     * @param newP 新密码
     * @return 是否成功
     */
    public boolean updatePassword(int id, String oldP, String newP) {
        if (userMapper.searchByID(id).getPassword().equals(oldP)) {
            userMapper.updatePassword(id, newP);
            return true;
        }
        return false;
    }

    /**
     * 查询用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    public User select(int id) {
        return userMapper.searchByID(id);
    }
}
