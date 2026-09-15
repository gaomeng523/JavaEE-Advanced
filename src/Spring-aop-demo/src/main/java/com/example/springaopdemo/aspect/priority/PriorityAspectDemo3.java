package com.example.springaopdemo.aspect.priority;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 切面优先级 @Order(3) —— 优先级最低
 * <p>
 * 观察入口：http://127.0.0.1:8080/priority/p1
 * <p>
 * 想验证"不加 @Order 时按类名字母排序"，把三个切面类上的 @Order 注释掉再跑一次，
 * 会变成按类名字母排序：Before 顺序 1 → 2 → 3，After 顺序 3 → 2 → 1。
 */
@Slf4j
@Aspect
@Component
@Order(3)
public class PriorityAspectDemo3 {

    @Pointcut("execution(* com.example.springaopdemo.controller.PriorityController.*(..))")
    private void pt() {
    }

    /**
     * 前置通知
     */
    @Before("pt()")
    public void doBefore() {
        log.info("执行 PriorityAspectDemo3 -> Before 方法");
    }

    /**
     * 后置通知
     */
    @After("pt()")
    public void doAfter() {
        log.info("执行 PriorityAspectDemo3 -> After 方法");
    }
}
