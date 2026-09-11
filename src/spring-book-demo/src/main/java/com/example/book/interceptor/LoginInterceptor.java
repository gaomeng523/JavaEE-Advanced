package com.example.book.interceptor;

import com.example.book.entity.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器：没登录的请求一律拦下来
 * 判断依据只有一句——session 里有没有 UserController 登录成功时写进去的 UserInfo
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    // session 里存登录用户的 key：登录时写入、拦截时读取，两边必须用同一个常量，所以由这里统一提供
    public static final String SESSION_USER_KEY = "session_user_key";

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1. 已经登录：session 里有登录用户，放行，请求继续交给 Controller
        //    这里用 getSession(false)，没登录时不白白创建 session
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(SESSION_USER_KEY) != null) {
            return true;
        }

        // 2. 没登录：ajax 和页面要区别对待，否则在地址栏直接打开页面会看到一坨 JSON
        log.warn("未登录访问被拦截，uri:{}", request.getRequestURI());
        if (isAjaxRequest(request)) {
            // ajax 请求：返回 401 + 统一返回体，前端的 error 回调里判断 401 后跳登录页
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(Result.fail(401, "未登录，请先登录")));
        } else {
            // 页面请求：直接重定向到登录页
            response.sendRedirect(request.getContextPath() + "/login.html");
        }
        // 返回 false，后面的 Controller 不会再执行
        return false;
    }

    // jQuery 的 $.ajax 默认会带 X-Requested-With 请求头，用它区分 ajax 请求和浏览器直接访问页面
    private boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
