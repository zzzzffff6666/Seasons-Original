package com.msjf.seasons.service;

import com.msjf.seasons.entity.Operator;
import com.msjf.seasons.entity.User;
import com.msjf.seasons.mapper.OperatorMapper;
import com.msjf.seasons.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ConnectService {
    @Resource
    private OperatorMapper operatorMapper;
    @Resource
    private UserMapper userMapper;

    public boolean login(String name, String password) {
        User u = userMapper.searchByName(name);
        if (u == null) return false;
        return u.getPassword().equals(password);
    }

    public boolean opLogin(String name, String password) {
        Operator op = operatorMapper.select(name);
        if (op == null) return false;
        return op.getPassword().equals(password);
    }

    public int register(String name, String password, String email) {
        User u = new User();
        u.setName(name);
        u.setPassword(password);
        u.setEmail(email);
        return userMapper.add(u);
    }
}
