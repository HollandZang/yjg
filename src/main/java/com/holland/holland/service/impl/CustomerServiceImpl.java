package com.holland.holland.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holland.holland.mapper.CustomerMapper;
import com.holland.holland.pojo.Customer;
import com.holland.holland.service.ICustomerService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 顾客信息表ServiceImpl
 * zhn
 * 2021-05-22
 */
@Service
public class CustomerServiceImpl implements ICustomerService {

    @Resource
    private CustomerMapper customerMapper;

    /**
     * 新增
     */
    @Override
    public void add(Customer record) throws Exception {
        customerMapper.insertSelective(record);
    }

    /**
     * 修改
     */
    @Override
    public void update(Customer record) throws Exception {
        customerMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 删除
     */
    @Override
    public void delete(Integer id) throws Exception {
        customerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Customer getModelByLoginName(String loginName) {
        return customerMapper.selectByLoginName(loginName);
    }

    /**
     * 获取列表
     */
    @Override
    public List<Customer> selectAll(Map map) throws Exception {
        return customerMapper.selectAllByMap(map);
    }

    /**
     * 获取分页数据
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
        List<Customer> list = customerMapper.selectAllByMap(map);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
