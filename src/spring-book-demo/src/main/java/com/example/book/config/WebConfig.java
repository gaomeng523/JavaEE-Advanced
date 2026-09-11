package com.example.book.config;

import com.example.book.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web 全局配置：目前只干一件事——把登录拦截器注册进去
 * 实现 WebMvcConfigurer 是 Spring MVC 提供的扩展点，不写这个类拦截器不会生效
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                // /** 会连静态资源一起拦，所以下面的放行名单一定要配对，漏一个就会跳转死循环
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/login.html",  // 登录页本身，不然永远跳不出去
                        "/user/login",  // 登录接口，不排除就永远登不进来
                        "/css/**",      // 下面三个是静态资源
                        "/js/**",
                        "/pic/**",
                        "/error",       // Spring Boot 的错误转发页，不排除会重定向死循环
                        "/favicon.ico"
                );
    }
}
