package com.example.config;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public DefaultWebSecurityManager securityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }

    @Bean
    public Realm realm() {
        UserRealm myShiroRealm = new UserRealm();
//        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }


    //用来对密码进行hash加密，如果密码为明文可以不设置
    @Bean
    public CredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
        credentialsMatcher.setStoredCredentialsHexEncoded(false);
        return credentialsMatcher;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/login");
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //添加Shiro的内置过滤器
        /*anon：无需认证就可以访问
        authc：必须认证了才能让问
        user：必须拥有，记住我功能，才能访问
        perms：拥有对某个资源的权限才能访问
        role：拥有某个角色权限才能访问*/
        filterChainDefinitionMap.put("/user/*", "authc");
        filterChainDefinitionMap.put("user/add","perms[user:add]");
        filterChainDefinitionMap.put("user/update","perms[user:update]");

        shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilter.setLoginUrl("/login");
//        shiroFilter.setSuccessUrl("/");
        // 没有授权的页面请求就会跳转到index页面请求
        shiroFilter.setUnauthorizedUrl("/noauth");
        return shiroFilter;
    }

    // 授权检验时可以使用注解，开启注解用
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

}
