package com.example.springaopdemo.controller;

import com.example.springaopdemo.annotation.MyAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @annotation 切点表达式演示用的第二个类
 * <p>
 * 只给 u1() 加自定义注解 @MyAspect，u2() 不加，用于对比切面是否生效。
 * <p>
 * 访问：http://127.0.0.1:8080/user/u1 （切面生效）
 * 　　　http://127.0.0.1:8080/user/u2 （切面不生效）
 */
@Slf4j
@RequestMapping("/user")
@RestController
public class UserController {

    @MyAspect
    @RequestMapping("/u1")
    public String u1() {
        log.info("执行目标方法 u1()");
        return "u1";
    }

    @RequestMapping("/u2")
    public String u2() {
        log.info("执行目标方法 u2()");
        return "u2";
    }
}
