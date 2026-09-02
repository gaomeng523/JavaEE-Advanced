package com.spring.ioc.controller;

import com.spring.ioc.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
//    @Autowired
//    private UserService userService;


    private UserService userService;

    public UserController(UserService userService){
        this.userService =userService;
    }


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void sayHi(){
        System.out.println("hi,UserController...");
    }
}
