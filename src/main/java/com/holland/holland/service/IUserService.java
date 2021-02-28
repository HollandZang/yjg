package com.holland.holland.service;

import com.github.pagehelper.PageInfo;
import com.holland.holland.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * Service
 * zhn
 * 2021-02-27
 */
public interface IUserService {

    User login(String user, String pwd);

    /**
     * 新增
     */
    void add(User record) throws Exception;

    /**
     * 修改
     */
    void update(User record) throws Exception;

    /**
     * 删除
     */
    void delete(Integer id) throws Exception;

    /**
     * 获取分页数据
     */
    PageInfo getList(Map map) throws Exception;

    /**
     * 获取列表数据
     */
    List<User> selectAll(Map map) throws Exception;

    /**
     * 获取实体
     */
    User getModel(Integer id) throws Exception;

    User getModelByUser(String user);

    List<User> getAll();
}
