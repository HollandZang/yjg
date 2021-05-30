package com.holland.holland.mapper;


import com.holland.holland.pojo.Log;
import com.holland.holland.pojo.LogLogin;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LogMapper {

    int insertSelective(Log record);

    List<Log> selectAllByMap(Map map);

    void insertSelectiveLogin(LogLogin record);

    List<LogLogin> selectAllLoginByMap(Map map);
}
