package com.example.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
    @RequestMapping("/login")
    public String toLogin(String username, String password, Model model) {
        //封装成Token令牌
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //获取当前的用户
        Subject subject = SecurityUtils.getSubject();
        try {
            //验证登录，如果没有异常就说明成功了
            subject.login(token);
            return "index";
        }catch (UnknownAccountException e){//用户名不存在
            model.addAttribute("msg", "用户名错误");
            return "login";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg", "密码错误");
            return "login";
        }
    }


}
