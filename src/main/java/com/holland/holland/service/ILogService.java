package com.holland.holland.service;

import com.github.pagehelper.PageInfo;
import com.holland.holland.pojo.Log;

import java.util.Map;

/**
 * LogService
 * zhn
 * 2021-02-27
 */
public interface ILogService {

    /**
     * 新增
     */
    void add(Log record) throws Exception;

    /**
     * 获取分页数据
     */
    PageInfo getList(Map map) throws Exception;

}
