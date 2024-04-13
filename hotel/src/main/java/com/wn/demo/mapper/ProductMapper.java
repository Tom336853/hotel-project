package com.wn.demo.mapper;

import com.wn.demo.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper {

    List<Product> selectAll(Product product);

    void delete(Integer id);

    Product selectOne(Integer id);

    int add(Product product);

    void update(Product product);

}
