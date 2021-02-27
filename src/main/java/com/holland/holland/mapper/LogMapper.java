package com.holland.holland.mapper;

import com.holland.holland.pojo.Log;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * LogMapper
 * zhn
 * 2021-02-27
 */
@Repository
public interface LogMapper {

    /**
     * 对非空字段插入
     */
    int insertSelective(Log record);

    /**
     * 获取数据
     */
    List<Log> selectAllByMap(Map map);
}
