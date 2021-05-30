package com.holland.holland.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
    public static String getToken(HttpServletRequest request) {
        return request.getHeader("token_fp");
    }

    public static String getUser(HttpServletRequest request) {
        return (String) request.getAttribute("user");
    }

    public static Integer getUserId(HttpServletRequest request) {
        return (Integer) request.getAttribute("userId");
    }
}
