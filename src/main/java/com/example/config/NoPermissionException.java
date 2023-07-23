package com.example.config;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


// 全局异常处理，用来捕获注解式抛出的UnauthorizedException并进行后续处理
@ControllerAdvice
public class NoPermissionException {

    @ExceptionHandler(UnauthorizedException.class)
    public String handleShiroException(Exception ex) {
        System.out.println("捕获到UnauthorizedException");
        return "noauth";
    }
}
