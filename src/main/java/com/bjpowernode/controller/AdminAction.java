package com.bjpowernode.controller;

import com.bjpowernode.pojo.Admin;
import com.bjpowernode.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminAction {
    @Autowired
    AdminService adminService;
    //实现判断，进行跳转
    @RequestMapping("/login")
    public String login(String name, String pwd, HttpServletRequest request){
        Admin admin=adminService.login(name,pwd);
        if (admin!=null) {
            request.setAttribute("admin",admin);
            return "main";
        }else {
            request.setAttribute("errmsg","用户名密码不正确！");
            return "login";
        }

    }
}
