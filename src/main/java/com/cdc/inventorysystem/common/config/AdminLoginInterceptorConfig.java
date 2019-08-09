package com.cdc.inventorysystem.common.config;

import com.cdc.inventorysystem.aop.AdminLoginInterceptor;
import com.cdc.inventorysystem.aop.UserLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class AdminLoginInterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    private AdminLoginInterceptor loginInterceptor;

    @Autowired
    private UserLoginInterceptor userLoginInterceptor;

    // 这个方法是用来配置静态资源的，比如html，js，css，等等
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示拦截所有的请求，
        // excludePathPatterns("/login", "/logout") 表示除了登陆与退出之外，因为登陆退出不需要登陆也可以访问
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/message/getAdminMessages","/userDetail/*")
                .excludePathPatterns("/admin/login", "/admin/logout");

        registry.addInterceptor(userLoginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/message/getAdminMessages","/user/register", "/user/login", "/user/logout","/admin/login", "/admin/logout","/userDetail/*",
                        "/mission/selectMissionAll","/message/getAdminMessages");
    }
}

