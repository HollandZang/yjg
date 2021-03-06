package com.holland.holland.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holland.holland.mapper.LogMapper;
import com.holland.holland.pojo.Log;
import com.holland.holland.pojo.LogLogin;
import com.holland.holland.service.ILogService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * LogServiceImpl
 * zhn
 * 2021-02-27
 */
@Service
public class LogServiceImpl implements ILogService {

    @Resource
    private LogMapper logMapper;

    /**
     * 新增
     */
    @Override
    public void add(Log record) throws Exception {
        logMapper.insertSelective(record);
    }

    /**
     * 获取分页数据
     */
    @Override
    public PageInfo getList(Map map) throws Exception {
        int pageNum = 1;
        int pageSize = 10;
        if (!StringUtils.isEmpty(map.get("page"))) {
            pageNum = Integer.parseInt((String) map.get("page"));
        }
        if (!StringUtils.isEmpty(map.get("limit"))) {
            pageSize = Integer.parseInt((String) map.get("limit"));
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Log> list = logMapper.selectAllByMap(map);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }

    @Override
    public void addLogin(LogLogin record) throws Exception {
        logMapper.insertSelectiveLogin(record);
    }

    @Override
    public PageInfo getListLogin(Map map) {
        int pageNum = 1;
        int pageSize = 10;
        if (!StringUtils.isEmpty(map.get("page"))) {
            pageNum = Integer.parseInt((String) map.get("page"));
        }
        if (!StringUtils.isEmpty(map.get("limit"))) {
            pageSize = Integer.parseInt((String) map.get("limit"));
        }
        PageHelper.startPage(pageNum, pageSize);
        List<LogLogin> list = logMapper.selectAllLoginByMap(map);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
