package com.holland.holland.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志表
 * @TableName log
 */
public class Log implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 区分前后端账号，01员工，02顾客
     */
    private String from;

    /**
     * 操作人
     */
    private String operateUser;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 操作类型 CRUD
     */
    private String operateType;

    /**
     * 操作api
     */
    private String operateApi;

    /**
     * ip来源
     */
    private String ip;

    /**
     *
     */
    private String param;

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

    public Log setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public Log setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getOperateUser() {
        return operateUser;
    }

    public Log setOperateUser(String operateUser) {
        this.operateUser = operateUser;
        return this;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public Log setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
        return this;
    }

    public String getOperateType() {
        return operateType;
    }

    public Log setOperateType(String operateType) {
        this.operateType = operateType;
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

    public String getParam() {
        return param;
    }

    public Log setParam(String param) {
        this.param = param;
        return this;
    }

    public Integer getResult() {
        return result;
    }

    public Log setResult(Integer result) {
        this.result = result;
        return this;
    }

    public String getResponse() {
        return response;
    }

    public Log setResponse(String response) {
        this.response = response;
        return this;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
