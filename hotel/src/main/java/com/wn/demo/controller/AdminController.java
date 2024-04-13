package com.wn.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wn.demo.mapper.AdminMapper;
import com.wn.demo.mapper.FinanceMapper;
import com.wn.demo.pojo.Admin;
import com.wn.demo.pojo.Finance;
import com.wn.demo.pojo.Product;
import com.wn.demo.service.ProductService;
import com.wn.demo.util.PageResultBean;
import com.wn.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminMapper adminMapper;

    @GetMapping("/index")
    public String index() {
        return "admin/admin-list";
    }

    @GetMapping
    public String add() {
        return "admin/admin-add";
    }

    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<Admin> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "limit", defaultValue = "10") int limit) {
        PageHelper.startPage(page, limit);
        List<Admin> sites = adminMapper.selectAll(null);
        PageInfo<Admin> userPageInfo = new PageInfo<>(sites);
        return new PageResultBean<>(userPageInfo.getTotal(), userPageInfo.getList());
    }

    @PostMapping
    @ResponseBody
    public ResultBean add(Admin admin, HttpServletRequest request) {
        String password = admin.getPassword();
        if (password!=null && !"".equals(password.trim())){
            admin.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        }
        return ResultBean.success(adminMapper.add(admin));
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("adminInfo", adminMapper.selectOne(id));
        return "admin/admin-add";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultBean update(Admin admin) {
        String password = admin.getPassword();
        if (password!=null && !"".equals(password.trim())){
            admin.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        }
        adminMapper.update(admin);
        return ResultBean.success();
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResultBean delete(@PathVariable("id") Integer id) {
        if (id==1) {
            return ResultBean.error("不能删除超级管理员");
        }
        adminMapper.delete(id);
        return ResultBean.success();
    }

}
