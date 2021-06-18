package com.zhang.seasonsgateway.model;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class SeasonsAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private String type;
    private int id;

    public SeasonsAuthenticationToken(Object principal, Object credentials, String type) {
        super(principal, credentials);
        this.type = type;
    }

    public SeasonsAuthenticationToken(Object principal, Object credentials, String type, int id,
                                      Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}
