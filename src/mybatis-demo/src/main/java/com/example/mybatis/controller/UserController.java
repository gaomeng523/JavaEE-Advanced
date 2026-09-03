package com.example.mybatis.controller;

import com.example.mybatis.entity.UserInfo;
import com.example.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @RequestMapping("getList")
    public List<UserInfo> getAllUser(){
        return userService.getAllUser();
    }
}
