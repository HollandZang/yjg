package com.holland.holland.service.impl;

import com.holland.holland.mapper.CodeMapper;
import com.holland.holland.pojo.Code;
import com.holland.holland.service.ICodeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * CodeServiceImpl
 * zhn
 * 2021-02-27
 */
@Service
public class CodeServiceImpl implements ICodeService {

    @Resource
    private CodeMapper codeMapper;

    @Override
    public List<Code> get(String type) throws Exception {
        return codeMapper.get(type);
    }

    @Override
    public List<Map> getTypes() {
        return codeMapper.getTypes();
    }
}
