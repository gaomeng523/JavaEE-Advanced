package com.example.springblogdemo.common.interceptor;

import com.example.springblogdemo.common.constants.Constant;
import com.example.springblogdemo.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 登录拦截器
 *
 * <p>职责：从请求头取出 JWT 校验合法性，并把解析出的用户 id 放入 request 域，
 * 供后续 Controller 使用（避免信任前端传入的 userId）。</p>
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    // JwtUtil 改为 Spring 组件后，需要通过构造器注入拿实例，不能再静态调用
    public LoginInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userToken = request.getHeader(Constant.USER_TOKEN_HEADER);

        // token 为空直接拒绝，注意这里不能继续往下调 parseJwt，否则会刷无意义的错误日志
        if (!StringUtils.hasText(userToken)) {
            log.warn("请求被拦截：未携带 token，uri={}", request.getRequestURI());
            response.setStatus(401);
            return false;
        }

        Claims claims = jwtUtil.parseJwt(userToken);
        if (claims == null) {
            log.warn("请求被拦截：token 解析失败，uri={}", request.getRequestURI());
            response.setStatus(401);
            return false;
        }

        // 把用户 id 存进 request 域，Controller 用 @RequestAttribute 即可取出
        // JWT 里的数字反序列化后可能是 Integer 也可能是 Long，统一转成 Integer 更稳妥
        Object idObj = claims.get(Constant.JWT_CLAIM_ID);
        if (idObj == null) {
            log.warn("请求被拦截：token 中缺少用户 id，uri={}", request.getRequestURI());
            response.setStatus(401);
            return false;
        }
        Integer userId = Integer.valueOf(idObj.toString());
        request.setAttribute(Constant.CURRENT_USER_ID, userId);

        // 成功放行时也打一行日志。
        // 之前这里没有日志，导致「登录状态下正常操作」在控制台看不到任何拦截器痕迹，
        // 很容易被误判成「拦截器没被调用」。有这行后，只要发出请求就能确认它跑了。
        log.info("登录校验通过，userId:{}, uri:{}, method:{}",
                userId, request.getRequestURI(), request.getMethod());

        return true;
    }
}
