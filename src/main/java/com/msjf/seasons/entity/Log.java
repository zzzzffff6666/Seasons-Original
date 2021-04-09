package com.msjf.seasons.entity;

import java.sql.Timestamp;

public class Log {
    private String uname;
    private Timestamp daytime;
    private String content;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
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
                "name='" + uname + '\'' +
                ", daytime=" + daytime +
                ", content='" + content + '\'' +
                '}';
    }
}
