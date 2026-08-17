package com.example.springmvc;


import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("request")
public class RequestController {
    @RequestMapping("/r1")
    public String r1(String s1) {
        return  "接收到的参数" + s1;
    }

    @RequestMapping("/r2")
    public String r2(Integer age){
        return "接收到的参数" + age;
    }

    @RequestMapping("/r3")
    public String r3(String name , Integer age){
        return String.format("接收到的参数： 姓名[%s] , age[%d]",name,age);
    }

    @RequestMapping("/r4")
    public String r4(@RequestParam("sa") String name){
        return  "接收到的参数" + name;
    }

    @RequestMapping("/r5")
    public String r5(Person p){
        return p.toString();
    }

    @RequestMapping("/r6")
    public String r6(int[] arr){
        return Arrays.toString(arr);
    }

    @RequestMapping("/r7")
    public String r7(@RequestParam List<String> array){
        return "array: " + array;
    }

    @RequestMapping("/r8")
    public String r8(@RequestBody Person person){
        return "就收到的参数" + person;
    }

    @RequestMapping("/{articleId}")
    public String r9(@PathVariable("articleId") Integer articleId){
        return "就收到的参数" + articleId;
    }
}
