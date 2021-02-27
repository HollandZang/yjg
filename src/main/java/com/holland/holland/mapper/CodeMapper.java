package com.holland.holland.mapper;

import com.holland.holland.pojo.Code;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CodeMapper {
    List<Code> get(String type);

    List<Map> getTypes();
}
