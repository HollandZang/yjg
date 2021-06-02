package com.holland.holland.mapper;

import com.holland.holland.pojo.Order;
import com.holland.holland.vo.OrderUpdateAllPermission;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Mapper
 * zhn
 * 2021-02-27
 */
@Repository
public interface OrderMapper {
    /**
     *全字段插入
     */
    int insert(Order record);

    /**
     *对非空字段插入
     */
    int insertSelective(Order record);

    /**
     *全字段更新
     */
    int updateByPrimaryKeySelective(Order record);
    int updateByPrimaryKeySelective_1(OrderUpdateAllPermission record);

    /**
     *对非空字段更新
     */
    int updateByPrimaryKey(Order record);

    /**
     *通过主键字段删除
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *通过主键字段查询
     */
    Order selectByPrimaryKey(Integer id);

    /**
     *获取数据
     */
    List<Order> selectAllByMap(Map map);
}
