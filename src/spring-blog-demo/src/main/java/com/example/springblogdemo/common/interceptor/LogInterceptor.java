package com.example.springblogdemo.common.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 请求观测拦截器（只负责打日志，不做任何鉴权）
 *
 * <p><b>它解决的问题：</b>之前排查问题时无法确认「拦截器到底有没有被调用」。
 * 因为 {@link LoginInterceptor} 只在「拦截失败」时打日志，登录状态下正常放行是完全静默的，
 * 控制台看不到任何痕迹，很容易误判成拦截器失效。</p>
 *
 * <p><b>为什么单独一个类，而不是在 LoginInterceptor 里加日志：</b>
 * 两者职责不同。LoginInterceptor 只挂 4 个写接口，它天然无法证明「全局拦截链路是通的」。
 * 这个类拦截 <b>/**</b>（全部路径），只要任意请求进来就会打一行日志，
 * 因此能直接证明「Spring MVC 的拦截器机制本身在工作」。</p>
 *
 * <p><b>重要：它没有任何鉴权能力</b>，永远返回 true。真正的登录校验仍然只看
 * {@link LoginInterceptor} 的 addPathPatterns 清单。<b>不要</b>因为它拦了全部路径
 * 就误以为「所有接口都受保护了」。</p>
 *
 * <p><b>排查完建议关掉：</b>它会对所有请求打日志，包括静态资源（css/js/html），
 * 正式使用时日志会比较吵。不需要时把下面 {@code addInterceptors} 里注册它的那两行
 * 注释掉即可，或把 {@code log.info} 降级为 {@code log.debug}。</p>
 */
@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("[拦截链路] {} {} 已进入拦截器", request.getMethod(), request.getRequestURI());
        // 只观测，不拦截。永远放行。
        return true;
    }
}
