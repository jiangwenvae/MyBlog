package com.zl.controller;

import com.zl.bean.Result;
import com.zl.bean.User;
import com.zl.config.Config;
import com.zl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
   @ResponseBody //表示响应对象
    public User testUser(){
        User user = userService.getUserById(1);
        return user;
    }

    /**
     * 登陆操作
     * @return
     */
    @RequestMapping("/login")
    public String login(){
        return "user/login";
    }

    /**
     * 验证登录信息
     */
    @RequestMapping("/checklogin")
    @ResponseBody
    public Result checklogin(User user, HttpSession session){
        System.out.println(user);
        boolean cheklogin = userService.cheklogin(user.getName(), user.getPasswd());
        Result result = new Result("fail", "用户名密码错误");
        if(cheklogin){
            //登记缓存信息
            result = new Result("success","成功");
            session.setAttribute(Config.LoginKey,user);
        }
        return result;
    }
}
