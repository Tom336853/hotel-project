package com.wn.demo.interceptor;

import com.wn.demo.pojo.Admin;
import com.wn.demo.pojo.User;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)throws Exception {

        //处于未登录状态，跳转到登录页面
        Admin user=(Admin) httpServletRequest.getSession().getAttribute("adminSession");

        User abc=(User) httpServletRequest.getSession().getAttribute("userSession");
        if (abc!=null){
            return true;
        }
        if(user==null){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login");
        }else{
            return true;
        }
        return false;
    }
 
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) {

    }
}