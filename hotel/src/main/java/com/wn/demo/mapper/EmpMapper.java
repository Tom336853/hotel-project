package com.wn.demo.mapper;

import com.wn.demo.pojo.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface EmpMapper {

    List<Emp> selectAll(Emp emp);

    void update(Emp emp);

    void delete(Integer id);

    Emp selectOne(Integer id);

    int add(Emp emp);

    Emp selectByNo(String empNo);
}
