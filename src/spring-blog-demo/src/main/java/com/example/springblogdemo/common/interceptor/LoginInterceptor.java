package com.example.springblogdemo.common.interceptor;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.springblogdemo.common.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userToken = request.getHeader("User-Token");
        log.info("userToken:{}", userToken);
        if(StringUtils.isEmpty(userToken) || JwtUtil.parseJwt(userToken) == null) {
            response.setStatus(401);
            return false;
        }
        return true;
    }
}
