package com.example.springaopdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 切面优先级（@Order）的目标类
 * <p>
 * 这里单独拆出一个 PriorityController，让 {@code aspect.priority} 下的 3 个切面只作用于它，
 * 不用再去注释别人的 @Component，日志也更干净。
 * <p>
 * 访问：http://127.0.0.1:8080/priority/p1
 */
@Slf4j
@RequestMapping("/priority")
@RestController
public class PriorityController {

    @RequestMapping("/p1")
    public String p1() {
        log.info("=========== 目标方法 PriorityController.p1() 执行 ===========");
        return "p1";
    }
}
