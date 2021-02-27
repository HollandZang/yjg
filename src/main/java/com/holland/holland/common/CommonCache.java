package com.holland.holland.common;

import com.holland.holland.pojo.Code;
import com.holland.holland.service.ICodeService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class CommonCache {

    /**
     * code,name
     */
    public static Map<String, String> ROLE = new LinkedHashMap<>();

    @Resource
    private ICodeService codeService;

    @PostConstruct
    public void init() throws Exception {
        List<Code> codes = codeService.get("0001");
        codeService.get("0001").forEach(code -> ROLE.put(code.getCode(), code.getName()));
    }
}
