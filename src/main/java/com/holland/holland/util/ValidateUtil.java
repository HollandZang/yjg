package com.holland.holland.util;


import java.util.Formatter;

public class ValidateUtil {

    public static void notEmpty(Object field, String fieldName) {
        if (field == null || field.toString().isBlank()) {
            ParameterException.runException("字段[%s]不能为空", fieldName);
        }
    }

    public static void maxLength(Long field, int maxLen, String fieldName) {
        if (field == null) {
            return;
        }
        if (String.valueOf(field).length() > maxLen) {
            ParameterException.runException("字段[%s]长度不能超过[%s]个字节(一个中文算2个字节)", fieldName, maxLen);
        }
    }

    public static void maxLength(String field, int maxLen, String fieldName) {
        if (field == null) {
            return;
        }
        int len = 0;
        for (char c : field.toCharArray()) {
            len += c > 127 || c == 97 ? 2 : 1;
        }
        if (len > maxLen) {
            ParameterException.runException("字段[%s]长度不能超过[%s]个字节(一个中文算2个字节)", fieldName, maxLen);
        }
    }

    public static class ParameterException extends RuntimeException {
        public ParameterException(String message) {
            super(message);
        }

        public static void runException(String str, Object... args) {
            throw new ParameterException(new Formatter().format(str, args).toString());
        }
    }
}
