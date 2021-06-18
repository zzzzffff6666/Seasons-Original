package com.zhang.seasonsconnector.service;

import com.zhang.seasonsconnector.mapper.AdminMapper;
import com.zhang.seasonsconnector.mapper.MagMapper;
import com.zhang.seasonsconnector.mapper.UserMapper;
import com.zhang.seasonsconnector.model.Account;
import com.zhang.seasonsconnector.model.Admin;
import com.zhang.seasonsconnector.model.Magazine;
import com.zhang.seasonsconnector.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class SeasonsUserDetailsService implements UserDetailsService {
    private static final List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>() {{
        add(new SimpleGrantedAuthority("ROLE_ROOT"));
        add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        add(new SimpleGrantedAuthority("ROLE_USER"));
        add(new SimpleGrantedAuthority("ROLE_MAG"));
    }};


    @Resource
    private AdminMapper adminMapper;
    @Resource
    private MagMapper magMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return loadUserByUsername(s, "user");
    }

    public UserDetails loadUserByUsername(String s, String accountType) throws UsernameNotFoundException {
        Account account = null;
        if (accountType.equals("user")) {
            account = getUser(s);
        } else if (accountType.equals("admin")) {
            account = getMagazine(s);
        } else if (accountType.equals("mag")) {
            account = getAdmin(s);
        }

        if (account == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        return account;
    }

    public Account getUser(String param) throws UsernameNotFoundException {
        User user = userMapper.selectByOthers(param);
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        Account account = new Account();
        account.setType("user");
        account.setId(user.getId());
        account.setUsername(user.getName());
        account.setPassword(user.getPassword());
        account.setPhone(user.getPhone());
        account.setAuthorities(getAuths("user"));
        return account;
    }

    public Account getAdmin(String param) throws UsernameNotFoundException {
        Admin admin = adminMapper.selectByOthers(param);
        if (admin == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        Account account = new Account();
        account.setType("admin");
        account.setId(admin.getId());
        account.setUsername(admin.getName());
        account.setPassword(admin.getPassword());
        account.setPhone(admin.getPhone());
        account.setAuthorities(getAuths(admin.getId() == 0 ? "root" : "admin"));
        return account;
    }

    public Account getMagazine(String param) throws UsernameNotFoundException {
        Magazine mag = magMapper.selectByOthers(param);
        if (mag == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        Account account = new Account();
        account.setType("mag");
        account.setId(mag.getId());
        account.setUsername(mag.getName());
        account.setPassword(mag.getPassword());
        account.setPhone(mag.getPhone());
        account.setAuthorities(getAuths("mag"));
        return account;
    }

    public static List<SimpleGrantedAuthority> getAuths(String type) {
        List<SimpleGrantedAuthority> auths = new ArrayList<>();
        switch (type) {
            case "user":
                auths.add(authorities.get(2));
                break;
            case "admin":
                auths.add(authorities.get(1));
                break;
            case "mag":
                auths.add(authorities.get(3));
                break;
            case "root":
                auths.addAll(authorities);
                break;
        }
        return auths;
    }
}
