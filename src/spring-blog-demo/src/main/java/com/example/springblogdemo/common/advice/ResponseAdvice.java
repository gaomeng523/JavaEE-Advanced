package com.example.springblogdemo.common.advice;

import com.example.springblogdemo.pojo.response.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 统一响应包装
 *
 * <p>Controller 返回什么，最终都会被包成 {@code {code, errMsg, data}} 的结构。</p>
 */
@ControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    private final ObjectMapper objectMapper;

    public ResponseAdvice(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * 判断哪些 Controller 的返回值需要被包装
     *
     * <p>这里排除了 {@code springfox} / {@code springdoc} 这类文档框架的接口，
     * 否则 Swagger 页面会因为返回值被包了一层而渲染不出来。</p>
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        String className = returnType.getDeclaringClass().getName();
        return !className.contains("springfox")
                && !className.contains("springdoc")
                && !className.contains("Swagger");
    }

    /**
     * 真正做包装的地方
     *
     * <p>三种情况要区别对待：</p>
     * <ul>
     *   <li>已经是 Result 的（异常处理器返回的）→ 原样返回，避免套两层</li>
     *   <li>String 类型的 → 必须手动序列化成 JSON 字符串。
     *       因为 Spring 给 String 用的是 StringHttpMessageConverter，
     *       如果直接返回 Result 对象它没法转换，会报 ClassCastException</li>
     *   <li>其余对象 → 交给 Jackson 正常序列化</li>
     * </ul>
     */
    @SneakyThrows
    @Nullable
    @Override
    public Object beforeBodyWrite(@Nullable Object body,
                                  MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof Result<?>) {
            return body;
        }
        if (body instanceof String) {
            return objectMapper.writeValueAsString(Result.success(body));
        }
        return Result.success(body);
    }
}
