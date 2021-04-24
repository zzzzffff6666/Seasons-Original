package com.msjf.seasons.entity;

import java.sql.Timestamp;

public class Log {
    private int type;
    private Timestamp daytime;
    private String content;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Timestamp getDaytime() {
        return daytime;
    }

    public void setDaytime(Timestamp daytime) {
        this.daytime = daytime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Log{" +
                "type=" + type +
                ", daytime=" + daytime +
                ", content='" + content + '\'' +
                '}';
    }
}
