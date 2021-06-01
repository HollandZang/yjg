package com.holland.holland.service.impl;

import com.holland.holland.mapper.OrderMapper;
import com.holland.holland.pojo.Order;
import com.holland.holland.vo.OrderUpdate;
import com.holland.holland.service.IOrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * ServiceImpl
 * zhn
 * 2021-02-27
 */
@Service
public  class OrderServiceImpl implements IOrderService {

    @Resource
    private OrderMapper orderMapper;

    /**
     *新增
     */
    @Override
    public void add(Order record) throws Exception {
        orderMapper.insertSelective(record);
    }
    
    /**
     *修改
     */
    @Override
    public void update(Order record) throws Exception {
        orderMapper.updateByPrimaryKeySelective(record);
    }
	
	@Override
    public void update(OrderUpdate record) throws Exception {
        orderMapper.updateByPrimaryKeySelective_1(record);
    }
    
    /**
     *删除
     */
    @Override
    public void delete(Integer id) throws Exception {
        orderMapper.deleteByPrimaryKey(id);
    }
    
    /**
     *获取实体
     */
    @Override
    public Order getModel(Integer id) throws Exception {
        return orderMapper.selectByPrimaryKey(id);
    }

    /**
     *获取列表
     */
    @Override
    public List<Order> selectAll(Map map) throws Exception {
        return orderMapper.selectAllByMap(map);
    }

    /**
     *获取分页数据
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
        List<Order> list = orderMapper.selectAllByMap(map);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
