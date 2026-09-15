package com.example.springaopdemo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 使用 @annotation 切点表达式定义切点
 * <p>
 * 只对标注了自定义注解 {@code @MyAspect} 的方法生效。
 * <p>
 * 实现步骤：
 * 1. 编写自定义注解 → {@link com.example.springaopdemo.annotation.MyAspect}
 * 2. 使用 @annotation 表达式来描述切点 → 就是本类
 * 3. 在连接点的方法上添加自定义注解 → TestController.t1()、UserController.u1()
 * <p>
 * 验证：
 * http://127.0.0.1:8080/test/t1  切面执行
 * http://127.0.0.1:8080/test/t2  未加注解，切面通知不执行
 * http://127.0.0.1:8080/user/u1  切面执行
 * http://127.0.0.1:8080/user/u2  未加注解，切面通知不执行
 */
@Slf4j
@Component
@Aspect
public class MyAspectDemo {

    /**
     * 前置通知
     */
    @Before("@annotation(com.example.springaopdemo.annotation.MyAspect)")
    public void before() {
        log.info("MyAspect -> before ...");
    }

    /**
     * 后置通知
     */
    @After("@annotation(com.example.springaopdemo.annotation.MyAspect)")
    public void after() {
        log.info("MyAspect -> after ...");
    }
}
