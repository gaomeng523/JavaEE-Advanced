package com.example.springblogdemo.config;

import com.example.springblogdemo.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 配置
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    public WebConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    /**
     * 注册登录拦截器
     *
     * <p>拦截范围只包含「需要登录」的写操作接口；读接口（列表、详情、作者信息、
     * 作者文章数统计）保持公开，否则未登录用户连首页都打不开。
     * 写接口在下面的 addPathPatterns 中逐个列出，这样新增接口时不会意外漏掉鉴权。</p>
     *
     * <p>新增接口时的判断标准：<b>这个接口会按「当前登录人」返回数据、或修改数据吗？</b>
     * 会就加进来，只是读取公开数据的就不加。</p>
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns(
                        "/blog/addBlog",
                        "/blog/updateBlog",
                        "/blog/deleteBlog",      // 写接口，必须登录
                        "/user/getUserInfo"      // 查“当前登录人”信息，必须已登录
                        // 注意 /blog/countByUser 不在这里：它统计的是任意作者的公开文章数，
                        // 和「当前登录人」无关，匿名浏览详情页时也要能调用
                )
                // 登录接口本身当然不能被拦截
                .excludePathPatterns("/user/login");
    }
}
