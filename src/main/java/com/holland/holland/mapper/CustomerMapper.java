package com.holland.holland.mapper;

import com.holland.holland.pojo.Customer;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 顾客信息表Mapper
 * zhn
 * 2021-05-22
 */
@Mapper
public interface CustomerMapper {
    /**
     * 全字段插入
     */
    int insert(Customer record);

    /**
     * 对非空字段插入
     */
    int insertSelective(Customer record);

    /**
     * 全字段更新
     */
    int updateByPrimaryKeySelective(Customer record);

    /**
     * 通过主键字段删除
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 通过主键字段查询
     */
    Customer selectByLoginName(String loginName);

    /**
     * 获取数据
     */
    List<Customer> selectAllByMap(Map map);
}
