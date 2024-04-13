package com.wn.demo.mapper;

import com.wn.demo.pojo.Finance;
import com.wn.demo.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FinanceMapper {

    List<Finance> selectAll(Finance finance);

    void delete(Integer id);

    Finance selectOne(Integer id);

    int add(Finance finance);

    void update(Finance finance);

}
