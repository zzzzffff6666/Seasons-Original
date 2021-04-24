package com.msjf.seasons.service;

import com.msjf.seasons.entity.Admin;
import com.msjf.seasons.entity.Magazine;
import com.msjf.seasons.entity.User;
import com.msjf.seasons.mapper.AdminMapper;
import com.msjf.seasons.mapper.MagMapper;
import com.msjf.seasons.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ConnectService {
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private MagMapper magMapper;

    /**
     * 查询用户
     * @param param 用户名
     * @return 用户对象
     */
    public User userSearch(String param) {
        return userMapper.searchByName(param);
    }

    /**
     * 查询管理员
     * @param name 管理员名称
     * @return 管理员对象
     */
    public Admin opSearch(String name) {
        return adminMapper.select(name);
    }

    /**
     * 查询杂志社
     * @param email 杂志社邮件
     * @return 杂志社对象
     */
    public Magazine magSearch(String email) {
        return magMapper.select(email);
    }

    /**
     * 用户注册
     * @param name 用户名
     * @param password 密码
     * @param email 邮箱
     * @return 是否成功
     */
    public boolean userRegister(String name, String password, String email) {
        User u = new User();
        u.setName(name);
        u.setPassword(password);
        u.setEmail(email);
        return userMapper.insert(u) == 1;
    }

    /**
     * 杂志社注册
     * @param mag 杂志社对象
     * @return 是否成功
     */
    public boolean magRegister(Magazine mag) {
        return magMapper.insert(mag) == 1;
    }
}
