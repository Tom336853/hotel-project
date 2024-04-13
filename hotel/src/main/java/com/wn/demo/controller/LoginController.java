package com.wn.demo.controller;

import com.wn.demo.pojo.Admin;
import com.wn.demo.pojo.User;
import com.wn.demo.service.AdminService;
import com.wn.demo.service.UserService;
import com.wn.demo.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @GetMapping("login")
    public String String(){
        return "login";
    }

    @GetMapping("register")
    public String register(Model model){
        return "register";
    }

    @GetMapping("forget")
    public String forget(Model model){
        return "forget";
    }


    @GetMapping("logout")
    public String logout(HttpSession session,Model model){
        session.removeAttribute("userSession");
        session.removeAttribute("adminSession");
        return "redirect:front/index";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResultBean denglu(Admin admin, HttpSession session, HttpServletRequest request){
        if (admin.getUserType()==1){
            Admin ad = adminService.findByAdminName(admin.getName());
            if (ad!=null && DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()).equals(ad.getPassword())){
                session.setAttribute("adminSession" ,ad);
                return ResultBean.success("管理员登录成功");
            }
            return ResultBean.error("账户不存在或密码错误");
        }else {
            User ad = userService.findByPhone(admin.getName());
            if (ad!=null && DigestUtils.md5DigestAsHex(admin.getPassword().getBytes()).equals(ad.getPassword())){
                session.setAttribute("userSession", ad);
                return ResultBean.success("用户登录成功");
            }
            return ResultBean.error("账户不存在或密码错误");
        }
    }

    @PostMapping("register")
    @ResponseBody
    public ResultBean zhuce(User user){
        User abc = userService.findByPhone(user.getPhone()+"");
        if (abc!=null){
            return ResultBean.error("手机号已经被注册");
        }
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        int flag = userService.add(user);
        if (flag>0){
            return ResultBean.success("用户登录成功");
        }else {
            return ResultBean.error("注册失败");
        }
    }

    @PostMapping("forget")
    @ResponseBody
    public ResultBean forget(User user){
        User abc = userService.findByPhone(user.getPhone()+"");
        if (abc==null){
            return ResultBean.error("手机号不存在");
        }
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        int flag = userService.forget(user);
        if (flag>0){
            return ResultBean.success("修改成功");
        }else {
            return ResultBean.error("修改失败");
        }
    }
}
