package com.wn.demo.service;

import com.github.pagehelper.PageHelper;
import com.wn.demo.mapper.UserMapper;
import com.wn.demo.pojo.Admin;
import com.wn.demo.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<User> selectAll(int page, int rows, User user) {
        PageHelper.startPage(page, rows);
        List<User> siteList=userMapper.selectAll(user);
        return siteList;
    }

    public void update(User user) {
        userMapper.update(user);
    }

    @Transactional
    public void delete(Integer id) {
        User user = userMapper.selectOne(id);
        userMapper.delete(id);
    }

    public User selectOne(Integer id) {
        return userMapper.selectOne(id);
    }

    public Integer add(User user) {
        int flag = userMapper.add(user);
        return flag;
    }

    public User findByPhone(String phone) {
        return userMapper.findByPhone(phone);
    }

    public void addIntegral(User user) {
        userMapper.addIntegral(user);
    }

    public int forget(User user) {
        return userMapper.forget(user);
    }
}
