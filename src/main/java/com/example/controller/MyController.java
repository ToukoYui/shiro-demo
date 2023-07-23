package com.example.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MyController {

    @RequestMapping({"/","/index"})
    public String toIndex(Model model) {
        model.addAttribute("msg","hello,Shiro");
        return "index";
    }

    // 1.硬编码式
    @RequestMapping("/user/add")
    public String add() {
        System.out.println("进入add");
        Subject subject = SecurityUtils.getSubject();
        boolean isPermitted = subject.isPermitted("user:add");
        System.out.println("isPermitted = " + isPermitted);
        return isPermitted? "add":"noauth";
    }

    // 2 注解式
    @RequiresPermissions("user:update")
    @RequestMapping("/user/update")
    public String update() {
        // 这样是不能catch到异常的，需要@ExceptionHandler
//        try{
//            System.out.println("进入update");
//        }catch (UnauthorizedException e){
//            return "noauth";
//        }
        return "update";
    }


    @RequestMapping("/noauth")
    public String unauthorized(){
        return "noauth";
    }
}

