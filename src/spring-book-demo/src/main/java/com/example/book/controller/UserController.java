package com.example.book.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @RequestMapping("login")
    public Boolean login(String name , String password , HttpSession session){
        if(!StringUtils.hasLength(name) || !StringUtils.hasLength(password)){
            return false;
        }

        if(name.equals("admin") && password.equals("admin")){
            session.setAttribute("name" , name);
            return true;
        }
        return false;
    }
}
