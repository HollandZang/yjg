package com.holland.holland.service;

import com.github.pagehelper.PageInfo;
import com.holland.holland.pojo.Order;

import java.util.List;
import java.util.Map;

/**
 * Service
 * zhn
 * 2021-02-27
 */
public interface IOrderService {

    /**
     * 新增
     */
    void add(Order record) throws Exception;

    /**
     * 修改
     */
    void update(Order record) throws Exception;

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
    List<Order> selectAll(Map map) throws Exception;

    /**
     * 获取实体
     */
    Order getModel(Integer id) throws Exception;

}
