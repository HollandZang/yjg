package com.holland.holland.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.holland.holland.mapper.UserMapper;
import com.holland.holland.pojo.User;
import com.holland.holland.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * ServiceImpl
 * zhn
 * 2021-02-27
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User login(String user, String pwd) {
        return userMapper.login(user, pwd);
    }

    /**
     * 新增
     */
    @Override
    public void add(User record) throws Exception {
        userMapper.insertSelective(record);
    }

    /**
     * 修改
     */
    @Override
    public void update(User record) throws Exception {
        userMapper.updateByPrimaryKeySelective(record);
    }

    /**
     * 删除
     */
    @Override
    public void delete(Integer id) throws Exception {
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取实体
     */
    @Override
    public User getModel(Integer id) throws Exception {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User getModelByUser(String user) {
        return userMapper.getModelByUser(user);
    }


    /**
     * 获取列表
     */
    @Override
    public List<User> selectAll(Map map) throws Exception {
        return userMapper.selectAllByMap(map);
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
        List<User> list = userMapper.selectAllByMap(map);
        PageInfo pageInfo = new PageInfo(list);
        return pageInfo;
    }
}
