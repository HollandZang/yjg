package com.holland.holland.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * zhn
 * 2021-02-27
 */
public class User {

    private String token;

    private Integer id;
    private String user;
    private String pwd;
    private String name;

//    @JsonIgnore
    private String role;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;

//    private String[] roleArr;

    public String getToken() {
        return token;
    }

    public User setToken(String token) {
        this.token = token;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUser() {
        return user;
    }

    public User setUser(String user) {
        this.user = user;
        return this;
    }

    public String getPwd() {
        return pwd;
    }

    public User setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getRole() {
        return role;
    }

    public User setRole(String role) {
        this.role = role;
        return this;
    }

    public Date getcTime() {
        return cTime;
    }

    public User setcTime(Date cTime) {
        this.cTime = cTime;
        return this;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public User setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
        return this;
    }
}
