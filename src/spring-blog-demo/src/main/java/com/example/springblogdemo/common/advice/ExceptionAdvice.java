package com.example.springblogdemo.common.advice;

import com.example.springblogdemo.common.exception.BlogException;
import com.example.springblogdemo.pojo.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler
    public Result handler(Exception e) {
        log.error("发生异常，e:",e);
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler
    public Result handler(BlogException e) {
        log.error("发生异常，e:",e);
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public Result handler(HandlerMethodValidationException e) {
        log.error("参数校验失败");
        return Result.fail("参数不合法");
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public Result handler(MethodArgumentNotValidException e) {
        log.error("参数校验失败:{}",e.getBindingResult().getFieldError().getDefaultMessage());
        return Result.fail("参数不合法");
    }
}
