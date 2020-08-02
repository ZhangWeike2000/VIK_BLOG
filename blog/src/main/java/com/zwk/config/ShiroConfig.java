package com.zwk.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author mr.z
 * @date 2020/7/2 - 11:54
 */
@Configuration
public class ShiroConfig {
    //ShiroFileterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro内置过滤器
        /*
         anon:无需认证就开源访问
         authc:必须认证了才可以访问
         user:必须拥有 记住我 功能才能访问
         perms:拥有对某个资源的权限才能访问
         role:拥有某个权限后才能访问
         */
        //拦截
        Map<String,String> filetMap=new LinkedHashMap<>();
        //必须认证了才可以访问
        filetMap.put("/admin/login","anon");
        filetMap.put("/admin/**","authc");
        bean.setFilterChainDefinitionMap(filetMap);
        //设置登陆的请求
        bean.setLoginUrl("/admin");

        return bean;
    }

    //DafaultWebSecurityManager
    @Bean
    public DefaultWebSecurityManager  getDefaultWebSecurityManager(@Qualifier("userRelam") UserRelam userRelam){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        //关联userRelam
        securityManager.setRealm(userRelam);
        return  securityManager;
    }

    //创建realm对象 需要自定义
    @Bean
    public UserRelam userRelam() {
        return new UserRelam();
    }
    //整合
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
