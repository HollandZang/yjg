package com.holland.holland.service;

import com.holland.holland.pojo.Customer;
import com.github.pagehelper.PageInfo;
import com.holland.holland.pojo.User;

import java.util.Map;
import java.util.List;

/**
 * 顾客信息表Service
 * zhn
 * 2021-05-22
 */
public interface ICustomerService {

    /**
     * 新增
     */
    void add(Customer record) throws Exception;

    /**
     * 修改
     */
    void update(Customer record) throws Exception;

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
    List<Customer> selectAll(Map map) throws Exception;

    Customer getModelByLoginName(String loginName);
}
