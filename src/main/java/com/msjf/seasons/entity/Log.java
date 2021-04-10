package com.msjf.seasons.entity;

import java.sql.Timestamp;

public class Log {
    private String name;
    private Timestamp daytime;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                "name='" + name + '\'' +
                ", daytime=" + daytime +
                ", content='" + content + '\'' +
                '}';
    }
}
