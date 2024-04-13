package com.wn.demo.mapper;

import com.wn.demo.pojo.Admin;
import com.wn.demo.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    List<User> selectAll(User user);

    void update(User user);

    void delete(Integer id);

    User selectOne(Integer id);

    int add(User user);

    User findByPhone(String phone);

    void addIntegral(User user);

    int forget(User user);
}
