package com.example.springaopdemo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 跨切面类引用切点
 * <p>
 * AspectDemo 中的 pt() 是 public 的，所以这里可以直接用 "全限定类名.方法名()" 的方式引用：
 * {@code @Before("com.example.springaopdemo.aspect.AspectDemo.pt()")}
 * <p>
 * 运行后访问 http://127.0.0.1:8080/test/t1，
 * 除了 AspectDemo 的 5 条日志，还会多出这一条 "执行 AspectDemo2 -> Before 方法"，
 * 说明切点被另一个切面类复用了。
 */
@Slf4j
@Aspect
@Component
public class AspectDemo2 {

    /**
     * 前置通知（引用了 AspectDemo 中定义的切点）
     */
    @Before("com.example.springaopdemo.aspect.AspectDemo.pt()")
    public void doBefore() {
        log.info("执行 AspectDemo2 -> Before 方法");
    }
}
