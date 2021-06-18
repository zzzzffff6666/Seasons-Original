package com.zhang.seasonsconnector.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

public class SeasonsAuthenticationToken extends UsernamePasswordAuthenticationToken implements Serializable {
    private int id;

    private String type;
    private String ways;

    public SeasonsAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public SeasonsAuthenticationToken(int id, String type, Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWays() {
        return ways;
    }

    public void setWays(String ways) {
        this.ways = ways;
    }
}
