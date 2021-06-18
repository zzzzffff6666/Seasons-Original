package com.zhang.seasonsconnector.model;

public class Admin extends Common {
    private int id;

    public Admin() {
        setType("admin");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
