package com.msjf.seasons.service;

import com.msjf.seasons.entity.Operator;
import com.msjf.seasons.entity.User;
import com.msjf.seasons.mapper.OperatorMapper;
import com.msjf.seasons.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ConnectService {
    @Resource
    private OperatorMapper operatorMapper;
    @Resource
    private UserMapper userMapper;

    /**
     * 查询用户
     * @param name 用户名
     * @return 用户对象
     */
    public User userSearch(String name) {
        return userMapper.searchByName(name);
    }

    /**
     * 查询管理员
     * @param name 管理员名称
     * @return 管理员对象
     */
    public Operator opSearch(String name) {
        return operatorMapper.select(name);
    }

    /**
     * 用户注册
     * @param name 用户名
     * @param password 密码
     * @param email 邮箱
     * @return 是否成功
     */
    public boolean register(String name, String password, String email) {
        User u = new User();
        u.setName(name);
        u.setPassword(password);
        u.setEmail(email);
        return userMapper.add(u) == 1;
    }
}
