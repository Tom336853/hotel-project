package com.wn.demo.mapper;

import com.wn.demo.pojo.RestaurantOrder;
import com.wn.demo.pojo.RoomOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RestaurantOrderMapper {

    List<RestaurantOrder> selectAll(RestaurantOrder restaurantOrder);

    List<RestaurantOrder> selectAll2(RestaurantOrder restaurantOrder);

    void delete(Integer id);

    RestaurantOrder selectOne(Integer id);

    int add(RestaurantOrder restaurantOrder);

    int update(RestaurantOrder restaurantOrder);
}
