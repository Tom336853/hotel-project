package com.wn.demo.mapper;

import com.wn.demo.pojo.RoomOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoomOrderMapper {

    List<RoomOrder> selectAll(RoomOrder roomOrder);

    List<RoomOrder> selectAll2(RoomOrder roomOrder);

    void delete(Integer id);

    RoomOrder selectOne(Integer id);

    int add(RoomOrder roomOrder);

    int add2(RoomOrder roomOrder);

    int update(RoomOrder roomOrder);

}
