package com.zhang.seasonswork.model;

import java.sql.Timestamp;

public class Work {
    private int id;
    private String title;
    private String type;
    private String tag;
    private String content;
    private float price;
    private Timestamp daytime;
    private int uid;
    private int laud;
    private int store;
    private int state;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Timestamp getDaytime() {
        return daytime;
    }

    public void setDaytime(Timestamp daytime) {
        this.daytime = daytime;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getLaud() {
        return laud;
    }

    public void setLaud(int laud) {
        this.laud = laud;
    }

    public int getStore() {
        return store;
    }

    public void setStore(int store) {
        this.store = store;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
