package com.wn.demo.mapper;

import com.wn.demo.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminMapper {

    Admin findByAdminName(String name);

    List<Admin> selectAll(Admin admin);

    void delete(Integer id);

    Admin selectOne(Integer id);

    int add(Admin admin);

    void update(Admin admin);
}
