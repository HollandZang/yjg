package com.holland.holland.pojo;

import java.util.Date;

/**
 * zhn
 * 2021-02-27
 */
public class Log {

    private Integer id;
    private Integer operateUserId;
    private Date operateTime;
    private String operateApi;
    private String ip;

    public Integer getId() {
        return id;
    }

    public Log setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getOperateUserId() {
        return operateUserId;
    }

    public Log setOperateUserId(Integer operateUserId) {
        this.operateUserId = operateUserId;
        return this;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public Log setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
        return this;
    }

    public String getOperateApi() {
        return operateApi;
    }

    public Log setOperateApi(String operateApi) {
        this.operateApi = operateApi;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public Log setIp(String ip) {
        this.ip = ip;
        return this;
    }
}
