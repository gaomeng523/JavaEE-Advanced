package com.example.springaopdemo.aspect.priority;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 切面优先级 @Order(1) —— 优先级最高
 * <p>
 * 注意：com.example.springaopdemo.aspect 包里已经有一个 AspectDemo2 了，
 * Spring 组件扫描默认用"类名首字母小写"作为 Bean 名，如果这里也叫 AspectDemo2，
 * 会出现两个名为 aspectDemo2 的 Bean，启动直接报 ConflictingBeanDefinitionException。
 * 所以这个包里统一加了 Priority 前缀。
 * <p>
 * 预期日志顺序（访问 http://127.0.0.1:8080/priority/p1）：
 * PriorityAspectDemo1 -> Before 方法（1 最小，最先执行）
 * PriorityAspectDemo2 -> Before 方法（2）
 * PriorityAspectDemo3 -> Before 方法（3）
 * ---------- 执行目标方法 PriorityController.p1() ----------
 * PriorityAspectDemo3 -> After 方法（3 最大，最先执行）
 * PriorityAspectDemo2 -> After 方法（2）
 * PriorityAspectDemo1 -> After 方法（1）
 */
@Slf4j
@Aspect
@Component
@Order(1)
public class PriorityAspectDemo1 {

    @Pointcut("execution(* com.example.springaopdemo.controller.PriorityController.*(..))")
    private void pt() {
    }

    /**
     * 前置通知
     */
    @Before("pt()")
    public void doBefore() {
        log.info("执行 PriorityAspectDemo1 -> Before 方法");
    }

    /**
     * 后置通知
     */
    @After("pt()")
    public void doAfter() {
        log.info("执行 PriorityAspectDemo1 -> After 方法");
    }
}
