package com.wn.demo.controller;

import com.github.pagehelper.PageInfo;
import com.wn.demo.pojo.User;
import com.wn.demo.service.UserService;
import com.wn.demo.util.PageResultBean;
import com.wn.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String index() {
        return "user/user-list";
    }

    @GetMapping
    public String add() {
        return "user/user-add";
    }

    @GetMapping("/list")
    @ResponseBody
    public PageResultBean<User> getList(@RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam(value = "limit", defaultValue = "10") int limit,
                                              User user) {
        List<User> sites = userService.selectAll(page, limit, user);
        PageInfo<User> userPageInfo = new PageInfo<>(sites);
        return new PageResultBean<>(userPageInfo.getTotal(), userPageInfo.getList());
    }

    @PostMapping
    @ResponseBody
    public ResultBean add(User user) {
        return ResultBean.success(userService.add(user));
    }

    @GetMapping("/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", userService.selectOne(id));
        return "user/user-add";
    }

    @PostMapping("update")
    @ResponseBody
    public ResultBean update(User user, HttpSession session) {
        String password = user.getPassword();
        if (password!=null && !"".equals(password.trim())){
            user.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        }
        userService.update(user);
        session.removeAttribute("userSession");
        return ResultBean.success();
    }

    @PostMapping("/{id}")
    @ResponseBody
    public ResultBean delete(@PathVariable("id") Integer id) {
        userService.delete(id);
        return ResultBean.success();
    }
}
