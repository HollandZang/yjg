package com.holland.holland.pojo;


import java.util.Date;

/**
 * 顾客信息表
 * zhn
 * 2021-05-22
 */
public class Customer {

    private String token;

    /**
     *
     */
    private Integer id;

    /**
     * db: name
     */
    private String name;

    /**
     * db: login_name
     */
    private String user;

    /**
     *
     */
    private String pwd;

    /**
     *
     */
    private String phone;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    public String getToken() {
        return token;
    }

    public Customer setToken(String token) {
        this.token = token;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Customer setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }

    public String getUser() {
        return user;
    }

    public Customer setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPwd() {
        return pwd;
    }

    public Customer setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Customer setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Customer setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Customer setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
