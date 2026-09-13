package com.example.springaopdemo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class AspectDemo {
    @Around("execution(* com.example.springaopdemo.controller.*.*(..))")
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("do around 前");
        Object result = joinPoint.proceed();

        log.info("do around 后");
        return result;
    }


    @Before("execution(* com.example.springaopdemo.controller.*.*(..))")
    public void doBefore() throws Throwable{
        log.info("do before");
    }

    @After("execution(* com.example.springaopdemo.controller.*.*(..))")
    public void doAfter() throws Throwable{
        log.info("do after");
    }

    @AfterReturning("execution(* com.example.springaopdemo.controller.*.*(..))")
    public void doAfterReturning() throws Throwable{
        log.info("do AfterReturning");
    }

    @AfterThrowing("execution(* com.example.springaopdemo.controller.*.*(..))")
    public void doAfterThrowing() throws Throwable{
        log.info("do AfterThrowing");
    }
}
