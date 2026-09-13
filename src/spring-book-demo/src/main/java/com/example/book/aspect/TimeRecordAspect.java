package com.example.book.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class TimeRecordAspect {
    @Around("execution(* com.example.book.controller.*.*(..))")
    public Object recordTime(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        log.info(joinPoint.getSignature() + "耗时:{} ms",end-start);
        return result;
    }
}
