package com.holland.holland.util;

import com.alibaba.fastjson.JSONObject;
import com.holland.holland.pojo.Customer;
import com.holland.holland.pojo.User;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
    public static String getToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public static User getEmployee(HttpServletRequest request) {
        return JSONObject.parseObject((String) request.getAttribute("userStr"), User.class);
    }

    public static Customer getCustomer(HttpServletRequest request) {
        return JSONObject.parseObject((String) request.getAttribute("userStr"), Customer.class);
    }

    public static String getLoginName(HttpServletRequest request) {
        return (String) request.getAttribute("loginName");
    }

    public static Integer getUserId(HttpServletRequest request) {
        return (Integer) request.getAttribute("userId");
    }

    public static boolean isBackUser(HttpServletRequest request) {
        return "01".equals(request.getAttribute("from"));
    }

    public static String getFrom(HttpServletRequest request) {
        return (String) request.getAttribute("from");
    }
}
