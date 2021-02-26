package com.holland.holland.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

    private final Integer code;
    private final String msg;
    private final Object data;
    private final Long count;

    public static Response success(Object data, Long count) {
        return new Response(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMsg(), data, count);
    }

    public static Response success(Object data, String msg) {
        return new Response(ResultCodeEnum.SUCCESS.getCode(), msg, data, 0L);
    }

    public static Response success() {
        return new Response(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMsg(), "", 0L);
    }

    public static Response success(Long count) {
        return new Response(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMsg(), "", count);
    }

    public static Response success(Object data) {
        return new Response(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMsg(), data, 0L);
    }

    public static Response info(ResultCodeEnum code, Object data) {
        return new Response(code.getCode(), code.getMsg(), data, 0L);
    }

    public static Response error(String msg) {
        return new Response(ResultCodeEnum.SystemException.getCode(), msg, "", 0L);
    }

    public Response(ResultCodeEnum rCode) {
        this.code = rCode.getCode();
        this.msg = rCode.getMsg();
        this.data = "";
        this.count = 0L;
    }

    public String toJsonString() {
        return "{" + "\"code\":\"" + code + "\", \"msg\":\"" + msg + "\", \"data\": \"\", \"count\": 0}";
    }
}