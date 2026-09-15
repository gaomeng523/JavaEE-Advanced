package com.example.springaopdemo.aspect.priority;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 切面优先级 @Order(2) —— 优先级居中
 * <p>
 * 存在多个切面类、且都匹配到同一个目标方法时：
 * <ul>
 *     <li>不加 @Order：默认按切面类名的字母排序
 *         <ul>
 *             <li>@Before 通知：字母排名靠前的先执行</li>
 *             <li>@After  通知：字母排名靠前的后执行</li>
 *         </ul>
 *     </li>
 *     <li>加了 @Order：数字越小优先级越高
 *         <ul>
 *             <li>@Before 通知：数字越小先执行</li>
 *             <li>@After  通知：数字越大先执行</li>
 *         </ul>
 *     </li>
 * </ul>
 * 一句话：@Order 控制切面的优先级，先执行优先级较高的切面，再执行优先级较低的切面，最终执行目标方法。
 * <p>
 * 为简单起见，这里只写了 @Before 和 @After 两个通知。
 * <p>
 * 观察入口：http://127.0.0.1:8080/priority/p1
 */
@Slf4j
@Aspect
@Component
@Order(2)
public class PriorityAspectDemo2 {

    @Pointcut("execution(* com.example.springaopdemo.controller.PriorityController.*(..))")
    private void pt() {
    }

    /**
     * 前置通知
     */
    @Before("pt()")
    public void doBefore() {
        log.info("执行 PriorityAspectDemo2 -> Before 方法");
    }

    /**
     * 后置通知
     */
    @After("pt()")
    public void doAfter() {
        log.info("执行 PriorityAspectDemo2 -> After 方法");
    }
}
