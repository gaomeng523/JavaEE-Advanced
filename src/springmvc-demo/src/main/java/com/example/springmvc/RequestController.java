package com.example.springmvc;


import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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

    @RequestMapping("r11")
    public String r11(MultipartFile file) throws IOException {
         String name = file.getName();
         String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename:" + originalFilename);
        String contentType = file.getContentType();
        System.out.println("contentType:" + contentType);
        file.transferTo(new File("D:\\可清理\\" + originalFilename));
        return "接收到的文件" + name;
    }

    // 获取Cookie
    @RequestMapping("r12")
    public String r12(HttpServletRequest request , HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        StringBuilder builder = new StringBuilder();
        if(cookies != null) {
            for (Cookie ck:cookies) {
                builder.append(ck.getName() + "" +ck.getValue());
            }
        }
        return "Cookie信息" + builder;
    }
    @RequestMapping("setsess")
    public String setsess(HttpServletRequest request){
        HttpSession session = request.getSession();

        if(session != null){
            session.setAttribute("username" , "java");
        }
        return "session 存储成功";
    }
    @RequestMapping("sess")
    public String sess(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        String username = null;
        if(session != null && session.getAttribute("username") != null){
            username = (String) session.getAttribute("username");
        }
        return "username:" + username;
    }
}
