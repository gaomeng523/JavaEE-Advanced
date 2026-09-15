package com.example.springaopdemo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 统计 BookController 每个方法的执行时间 —— AOP 快速入门
 * <p>
 * 三部分解读：
 * 1. @Aspect  ：标识这是一个切面类
 * 2. @Around  ：环绕通知，在目标方法的前后都会执行，后面的表达式表示对哪些方法进行增强
 * 3. ProceedingJoinPoint.proceed()：让原始方法执行
 * <p>
 * 注意事项：
 * - @Around 必须调用 pjp.proceed()，否则原始方法不会执行
 * - @Around 的返回值必须声明为 Object，用来接收原始方法的返回值
 */
@Slf4j
@Aspect
@Component
public class TimeAspect {

    /**
     * 记录方法耗时
     * 切点表达式说明：com.example.springaopdemo.controller.book 包下所有类的所有方法
     */
    @Around("execution(* com.example.springaopdemo.controller.BookController.*(..))")
    public Object recordTime(ProceedingJoinPoint pjp) throws Throwable {
        // 记录方法执行开始时间
        long begin = System.currentTimeMillis();
        // 执行原始方法
        Object result = pjp.proceed();
        // 记录方法执行结束时间
        long end = System.currentTimeMillis();
        // 记录方法执行耗时
        log.info(pjp.getSignature() + " 执行耗时: {}ms", end - begin);
        return result;
    }
}
