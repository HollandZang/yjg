package com.holland.holland.common;

import com.holland.holland.pojo.User;
import com.holland.holland.service.ICodeService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CommonCache {

    public static Map<Integer, User> USER_MAP = new HashMap<>();

    /**
     * code,name
     */
    public static Map<String, String> ROLE = new LinkedHashMap<>();
    public static Map<String, String> ORDER_STATUS_1 = new LinkedHashMap<>();
    public static Map<String, String> ORDER_STATUS_2 = new LinkedHashMap<>();
    public static Map<String, String> ORDER_STATUS_3 = new LinkedHashMap<>();

    @Resource
    private ICodeService codeService;

    @PostConstruct
    public void init() throws Exception {
        codeService.get("0001").forEach(code -> ROLE.put(code.getCode(), code.getName()));

        codeService.get("0002").forEach(code -> ORDER_STATUS_1.put(code.getCode(), code.getName()));
        codeService.get("0003").forEach(code -> ORDER_STATUS_2.put(code.getCode(), code.getName()));
        codeService.get("0004").forEach(code -> ORDER_STATUS_3.put(code.getCode(), code.getName()));
    }
}
