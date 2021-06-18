package com.zhang.seasonsconnector.model;

public class Magazine extends Common {
    private int id;

    public Magazine() {
        setType("mag");
    }

    private String license;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
