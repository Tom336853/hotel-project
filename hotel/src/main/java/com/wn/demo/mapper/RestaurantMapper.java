package com.wn.demo.mapper;

import com.wn.demo.pojo.Restaurant;
import com.wn.demo.pojo.Room;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RestaurantMapper {

    List<Restaurant> selectAll(Restaurant restaurant);

    List<Restaurant> selectAll2(Restaurant restaurant);

    void delete(Integer id);

    Restaurant selectOne(Integer id);

    int add(Restaurant restaurant);

    Restaurant selectByRestNo(String restNo);

    void update(Restaurant restaurant);
}
