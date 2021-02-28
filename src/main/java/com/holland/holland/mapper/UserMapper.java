package com.holland.holland.mapper;

import com.holland.holland.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Mapper
 * zhn
 * 2021-02-27
 */
@Repository
public interface UserMapper {
    /**
     *全字段插入
     */
    int insert(User record);

    /**
     *对非空字段插入
     */
    int insertSelective(User record);

    /**
     *全字段更新
     */
    int updateByPrimaryKeySelective(User record);

    /**
     *对非空字段更新
     */
    int updateByPrimaryKey(User record);

    /**
     *通过主键字段删除
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *通过主键字段查询
     */
    User selectByPrimaryKey(Integer id);

    /**
     *获取数据
     */
    List<User> selectAllByMap(Map map);

    User login(String user, String pwd);

    User getModelByUser(String user);

    List<User> getAll();
}
