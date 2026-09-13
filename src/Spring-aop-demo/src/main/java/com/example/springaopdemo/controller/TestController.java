package com.example.springaopdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RequestMapping("Test")
@RestController
public class TestController {
    @RequestMapping("t1")
    public String t1() {
        return "t1";
    }

    @RequestMapping("t2")
    public Boolean t2() {
        log.info("t2");
        int a = 10 / 0;
        return true;
    }
}
