package com.example.springmvc;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello")
public class HelloController {
//    @RequestMapping(value = "/hello1" , method = RequestMethod.GET) ==
    @GetMapping("/hello1")
    public String hello1() {
        return "hello , spring mvc";
    }
    @RequestMapping("/hello2")
    public String hello2() {
        return "hello , spring mvc2";
    }

    @PostMapping("/hello3")
    public String hello3() {
        return "hello , spring mvc3";
    }
}
