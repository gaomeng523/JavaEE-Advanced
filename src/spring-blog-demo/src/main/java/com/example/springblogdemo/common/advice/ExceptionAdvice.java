package com.example.springblogdemo.common.advice;

import com.example.springblogdemo.common.exception.BlogException;
import com.example.springblogdemo.pojo.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

/**
 * 全局异常处理器
 *
 * <p>把各类异常统一翻译成 {@link Result} 结构返回给前端，
 * 避免把 Java 堆栈和原始报错信息直接暴露出去。</p>
 */
@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 业务异常：属于可预期的错误（用户不存在、密码错误等），
     * 用 warn 级别记录即可，不需要打印完整堆栈。
     */
    @ExceptionHandler(BlogException.class)
    public Result handler(BlogException e) {
        log.warn("业务异常：{}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    /**
     * 参数校验失败（方法参数上的 @NotNull 等，例如 BlogController 的 blogId）
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public Result handler(HandlerMethodValidationException e) {
        log.warn("参数校验失败：{}", e.getMessage());
        return Result.fail("参数不合法");
    }

    /**
     * 参数校验失败（@RequestBody 对象内的字段约束，例如 AddBlogRequest 的 title）
     */
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) {
        // 取第一条字段错误信息，注意 getFieldError() 可能为 null（比如是类级别的约束）
        FieldError fieldError = e.getBindingResult().getFieldError();
        String message = fieldError == null ? "参数不合法" : fieldError.getDefaultMessage();
        log.warn("参数校验失败：{}", message);
        return Result.fail(message);
    }

    /**
     * 兜底处理：其他所有未预期的异常
     *
     * <p>这里才需要打印完整堆栈。返回给前端的消息做脱敏，
     * 不要把 e.getMessage() 直接抛出去，它可能包含 SQL、表名等敏感信息。</p>
     */
    @ExceptionHandler(Exception.class)
    public Result handler(Exception e) {
        log.error("系统异常", e);
        return Result.fail("系统开小差了，请稍后重试");
    }
}
