package com.dhy.logswitch.demo.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "NetLog")
public class RequestLog {
    @PrimaryKey
    public Integer id;
    public long date;
    public String unique;
    public String server;
    public int httpCode;
    public String path;
    public String params;
    public String cmd;
    public String response;

}
