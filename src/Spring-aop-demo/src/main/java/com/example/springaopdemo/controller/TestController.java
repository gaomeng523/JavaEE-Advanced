package com.example.springaopdemo.controller;

import com.example.springaopdemo.annotation.MyAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通知类型演示的目标类
 * <p>
 * t1() 正常返回，用于观察"正常执行"时各通知的顺序；
 * t2() 抛出算术异常（10/0），用于观察 @AfterThrowing 何时执行、@AfterReturning 何时不执行。
 * <p>
 * 访问：http://127.0.0.1:8080/test/t1
 * 　　　http://127.0.0.1:8080/test/t2
 * <p>
 * 注意：这个类同时被 aspect.AspectDemo（5 种通知）、aspect.AspectDemo2（复用切点）、
 * aspect.MyAspectDemo（@annotation，只有 t1 有注解）匹配，所以访问 /test/t1 会看到多组日志。
 * 想单独观察某一个切面的效果时，把对应切面类上的 @Component 注释掉即可。
 */
@Slf4j
@RequestMapping("/test")
@RestController
public class TestController {

    /** 加自定义注解，@annotation 切点表达式就会匹配到这个方法 */
    @MyAspect
    @RequestMapping("/t1")
    public String t1() {
        log.info("---------- 执行目标方法 TestController.t1() ----------");
        return "t1";
    }

    @RequestMapping("/t2")
    public boolean t2() {
        log.info("---------- 执行目标方法 TestController.t2() ----------");
        // 故意制造异常，观察 @AfterThrowing 与 @AfterReturning 的差异
        int a = 10 / 0;
        return true;
    }
}
