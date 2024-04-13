package com.wn.demo.controller;

import com.github.pagehelper.PageInfo;
import com.wn.demo.mapper.EmpMapper;
import com.wn.demo.mapper.FinanceMapper;
import com.wn.demo.pojo.*;
import com.wn.demo.service.ProductService;
import com.wn.demo.util.PageResultBean;
import com.wn.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    FinanceMapper financeMapper;

    @Autowired
    EmpMapper empMapper;

    @GetMapping("/index")
    public String index() {
        return "product/product-list";
    }

    @GetMapping
    public String add(Model model) {
        List<Emp> empList = empMapper.selectAll(null);
        model.addAttribute("empList", empList);
        return "product/product-add";
    }

    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<Product> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "limit", defaultValue = "10") int limit,
                                           Product product) {
        List<Product> sites = productService.selectAll(page, limit, product);
        PageInfo<Product> userPageInfo = new PageInfo<>(sites);
        return new PageResultBean<>(userPageInfo.getTotal(), userPageInfo.getList());
    }

    @PostMapping
    @ResponseBody
    public ResultBean add(Product product, HttpServletRequest request) {
        Finance finance = new Finance();
        finance.setDataType("支出");
        finance.setInfo(product.getContent());
        finance.setMoney(product.getPrice()+"");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        finance.setMonthDate(sdf.format(new Date()));
        financeMapper.add(finance);
        return ResultBean.success(productService.add(product));
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        List<Emp> empList = empMapper.selectAll(null);
        model.addAttribute("empList", empList);
        model.addAttribute("productInfo", productService.selectOne(id));
        return "product/product-add";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultBean update(Product product) {
        productService.update(product);
        return ResultBean.success();
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResultBean delete(@PathVariable("id") Integer id) {
        productService.delete(id);
        return ResultBean.success();
    }

}
