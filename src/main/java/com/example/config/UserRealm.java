package com.example.config;

import com.example.entity.User;
import com.example.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

//自定义的UserRealm
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了=》授权doGetAuthorizationInfo");
        //注意区分：SimpleAuthenticationInfo和SimpleAuthorizationInfo
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();


        //拿到当前登录的这个对象
        Subject subject = SecurityUtils.getSubject();
        System.out.println("subject = " + subject.getPrincipal());

        //强制转换为User
        User currentUser = (User) subject.getPrincipal();

        //设置当前用户的权限
        info.addStringPermission(currentUser.getPerms());

        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了=>认证doGetAuthorizationInfo");
        //创建令牌
        UsernamePasswordToken usertoken = (UsernamePasswordToken) authenticationToken;

        //根据用户名查询数据库中的用户数据
        User user = userService.getByUsername(usertoken.getUsername());

        if (user == null) {//没有找到这个账号
            return null;
        }
        //可以使用加密 MD5
        //密码认证，shiro做
        return new SimpleAuthenticationInfo(user, user.getPassword(), "");
    }
}

