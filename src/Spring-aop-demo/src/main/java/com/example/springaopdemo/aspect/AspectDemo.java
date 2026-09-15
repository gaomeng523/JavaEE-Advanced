package com.example.springaopdemo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 五种通知类型 + @Pointcut 复用切点表达式
 * <p>
 * 五种通知类型：
 * ● @Around       环绕通知：目标方法前、后都执行
 * ● @Before       前置通知：目标方法执行前执行
 * ● @After        后置通知：目标方法执行后执行，无论是否有异常都会执行
 * ● @AfterReturning 返回后通知：目标方法正常返回后执行，有异常不会执行
 * ● @AfterThrowing  异常后通知：目标方法发生异常后执行
 * <p>
 * 切点表达式在多个通知里重复出现，可以提取成 @Pointcut("pt()") 统一复用。
 * <p>
 * 观察结论（访问 http://127.0.0.1:8080/test/t1）：
 * 1. 正常执行时，@AfterThrowing 不会执行
 * 2. @Around 分"前置逻辑"和"后置逻辑"：
 * 前置逻辑先于 @Before 执行，后置逻辑晚于 @After 执行
 * <p>
 * 访问 http://127.0.0.1:8080/test/t2（抛出异常）：
 * ● @AfterReturning 不执行，@AfterThrowing 执行
 * ● @Around 中 proceed() 抛异常后，环绕后的代码也不再执行
 */
@Slf4j
@Aspect
@Component
public class AspectDemo {

    /**
     * 定义切点（公共的切点表达式）
     * <p>
     * 当切点定义使用 private 修饰时，仅能在当前切面类中使用；
     * 其他切面类也要使用该切点时，需要把 private 改为 public，引用方式为：全限定类名.方法名()
     * 见 {@link AspectDemo2}
     */
    @Pointcut("execution(* com.example.springaopdemo.controller.TestController.*(..))")
    public void pt() {
    }

    /**
     * 前置通知
     */
    @Before("pt()")
    public void doBefore() {
        log.info("执行 Before 方法");
    }

    /**
     * 后置通知（无论是否有异常都会执行）
     */
    @After("pt()")
    public void doAfter() {
        log.info("执行 After 方法");
    }

    /**
     * 返回后通知（有异常不会执行）
     */
    @AfterReturning("pt()")
    public void doAfterReturning() {
        log.info("执行 AfterReturning 方法");
    }

    /**
     * 抛出异常后通知
     */
    @AfterThrowing("pt()")
    public void doAfterThrowing() {
        log.info("执行 doAfterThrowing 方法");
    }

    /**
     * 环绕通知
     */
    @Around("pt()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Around 方法开始执行");
        Object result = joinPoint.proceed();
        log.info("Around 方法结束执行");
        return result;
    }
}
