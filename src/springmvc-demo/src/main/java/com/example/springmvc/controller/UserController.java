package com.example.springmvc.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @RequestMapping("login")
    public boolean login(String userName , String password , HttpSession session){
        if(!StringUtils.hasLength(userName) || !StringUtils.hasLength(password)){
            return false;
        }
        // 判断账号和密码是否正确, 还未学习数据库，这里先写死
        if(userName.equals("admin") || password.equals("123456")){
            session.setAttribute("userName" , userName);
            return true;
        }
        return false;
    }

    @RequestMapping("getLoginUser")
    public String getLoginUser(HttpSession session){
        String userName = (String)session.getAttribute("userName");
        if(StringUtils.hasLength(userName)){
            return userName;
        }
        return "";
    }
}
