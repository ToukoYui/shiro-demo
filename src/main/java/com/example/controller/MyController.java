package com.example.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

    @RequestMapping({"/","/index"})
    public String toIndex(Model model) {
        model.addAttribute("msg","hello,Shiro");
        return "index";
    }

    @RequestMapping("/user/add")
    public String add() {
        System.out.println("进入add");
        Subject subject = SecurityUtils.getSubject();
        boolean isPermitted = subject.isPermitted("user:add");
        System.out.println("isPermitted = " + isPermitted);
        return isPermitted? "add":"noauth";
    }

    @RequiresPermissions("user:update")
    @RequestMapping("/user/update")
    public String update() {
        System.out.println("进入update");
//        Subject subject = SecurityUtils.getSubject();
//        boolean isPermitted = subject.isPermitted("user:update");
//        System.out.println("isPermitted = " + isPermitted);
//        return isPermitted? "update":"noauth";
        return "update";
    }

    @RequestMapping("/noauth")
    public String unauthorized(){
        return "noauth";
    }
}

