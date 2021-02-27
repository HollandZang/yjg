package com.holland.holland.service;

import com.holland.holland.pojo.Code;

import java.util.List;
import java.util.Map;

/**
 * CodeService
 * zhn
 * 2021-02-27
 */
public interface ICodeService {

    List<Code> get(String type) throws Exception;

    List<Map> getTypes();
}
