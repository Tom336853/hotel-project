package com.wn.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wn.demo.mapper.EmpMapper;
import com.wn.demo.pojo.Emp;
import com.wn.demo.util.PageResultBean;
import com.wn.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/emp")
public class EmpController {

    @Autowired
    EmpMapper empMapper;

    @GetMapping("/index")
    public String index() {
        return "emp/emp-list";
    }

    @GetMapping
    public String add() {
        return "emp/emp-add";
    }

    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<Emp> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                       @RequestParam(value = "limit", defaultValue = "10") int limit) {
        PageHelper.startPage(page, limit);
        List<Emp> sites = empMapper.selectAll(null);
        PageInfo<Emp> userPageInfo = new PageInfo<>(sites);
        return new PageResultBean<>(userPageInfo.getTotal(), userPageInfo.getList());
    }

    @PostMapping
    @ResponseBody
    public ResultBean add(Emp emp, HttpServletRequest request) {
        return ResultBean.success(empMapper.add(emp));
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("empInfo", empMapper.selectOne(id));
        return "emp/emp-add";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultBean update(Emp emp) {
        empMapper.update(emp);
        return ResultBean.success();
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResultBean delete(@PathVariable("id") Integer id) {
        empMapper.delete(id);
        return ResultBean.success();
    }

}
