package com.wn.demo.mapper;

import com.wn.demo.pojo.Room;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoomMapper {

    List<Room> selectAll(Room room);

    List<Room> selectAll2(Room room);

    void delete(Integer id);

    Room selectOne(Integer id);

    int add(Room room);

    Room selectByRoomNo(String roomNo);

    void update(Room room);

}
