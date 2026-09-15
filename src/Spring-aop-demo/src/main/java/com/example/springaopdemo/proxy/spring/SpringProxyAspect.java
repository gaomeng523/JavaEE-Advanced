package com.example.springaopdemo.proxy.spring;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 让 proxy.spring 包下的 Bean 被 AOP 管理
 * <p>
 * 要把 Bean 交给 AOP 管理（自定义注解或使用 @Aspect），Spring 才会为其创建代理对象。
 * 没有切面匹配时 getBean 拿到的就是原始类型，看不出任何效果，所以这里补一个切面。
 */
@Slf4j
@Aspect
@Component
public class SpringProxyAspect {

    @Around("execution(* com.example.springaopdemo.proxy.spring..*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        log.info("[SpringProxyAspect] 前置逻辑 -> {}", pjp.getSignature().toShortString());
        Object result = pjp.proceed();
        log.info("[SpringProxyAspect] 后置逻辑 -> {}", pjp.getSignature().toShortString());
        return result;
    }
}
