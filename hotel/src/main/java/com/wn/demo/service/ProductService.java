package com.wn.demo.service;

import com.github.pagehelper.PageHelper;
import com.wn.demo.mapper.ProductMapper;
import com.wn.demo.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;

    public List<Product> selectAll(int page, int rows, Product product) {
        PageHelper.startPage(page, rows);
        List<Product> siteList= productMapper.selectAll(product);
        return siteList;
    }

    public List<Product> selectAll(Product product) {
        List<Product> siteList= productMapper.selectAll(product);
        return siteList;
    }

    public void update(Product product) {
        productMapper.update(product);
    }

    public void delete(Integer id) {
        productMapper.delete(id);
    }

    public Product selectOne(Integer id) {
        return productMapper.selectOne(id);
    }

    public String add(Product product) {
        int flag = productMapper.add(product);
        if (flag>0){
            return "添加成功";
        }
        return "添加失败";
    }
}
