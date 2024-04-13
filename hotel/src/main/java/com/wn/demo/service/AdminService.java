package com.wn.demo.service;

import com.wn.demo.mapper.AdminMapper;
import com.wn.demo.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public Admin findByAdminName(String name){
        return adminMapper.findByAdminName(name);
    }
}
