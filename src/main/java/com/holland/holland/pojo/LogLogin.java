package com.holland.holland.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志表
 * @TableName log_login
 */
public class LogLogin implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 操作人
     */
    private String operateUser;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 操作类型 1: 登录  0: 登出
     */
    private String operateType;

    /**
     * 指明通过什么软件、项目登录
     */
    private String from;

    /**
     * ip来源
     */
    private String ip;

    /**
     *
     */
    private Integer result;

    /**
     *
     */
    private String response;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public LogLogin setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public LogLogin setOperateUser(String operateUser) {
        this.operateUser = operateUser;
        return this;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public LogLogin setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
        return this;
    }

    public String getOperateType() {
        return operateType;
    }

    public LogLogin setOperateType(String operateType) {
        this.operateType = operateType;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public LogLogin setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public LogLogin setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public Integer getResult() {
        return result;
    }

    public LogLogin setResult(Integer result) {
        this.result = result;
        return this;
    }

    public String getResponse() {
        return response;
    }

    public LogLogin setResponse(String response) {
        this.response = response;
        return this;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
