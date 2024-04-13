package com.wn.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wn.demo.mapper.FinanceMapper;
import com.wn.demo.pojo.Finance;
import com.wn.demo.util.PageResultBean;
import com.wn.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/finance")
public class FinanceController {

    @Autowired
    FinanceMapper financeMapper;

    @GetMapping("/index")
    public String index() {
        return "finance/finance-list";
    }

    @GetMapping
    public String add() {
        return "finance/finance-add";
    }

    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<Finance> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "limit", defaultValue = "10") int limit) {
        PageHelper.startPage(page, limit);
        List<Finance> sites = financeMapper.selectAll(null);
        PageInfo<Finance> userPageInfo = new PageInfo<>(sites);
        return new PageResultBean<>(userPageInfo.getTotal(), userPageInfo.getList());
    }

    @PostMapping
    @ResponseBody
    public ResultBean add(Finance finance, HttpServletRequest request) {
        return ResultBean.success(financeMapper.add(finance));
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("financeInfo", financeMapper.selectOne(id));
        return "finance/finance-add";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultBean update(Finance finance) {
        financeMapper.update(finance);
        return ResultBean.success();
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResultBean delete(@PathVariable("id") Integer id) {
        financeMapper.delete(id);
        return ResultBean.success();
    }

}
