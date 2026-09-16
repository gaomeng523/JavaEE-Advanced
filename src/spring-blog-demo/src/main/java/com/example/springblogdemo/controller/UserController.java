package com.example.springblogdemo.controller;

import com.example.springblogdemo.pojo.request.UserLoginRequest;
import com.example.springblogdemo.pojo.response.UserInfoResponse;
import com.example.springblogdemo.pojo.response.UserLoginResponse;
import com.example.springblogdemo.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public UserLoginResponse login(@Validated @RequestBody UserLoginRequest userLoginRequest) {
        log.info("用户登录，username:{}",userLoginRequest.getUserName());
        return userService.login(userLoginRequest);
    }

    @GetMapping("getUserInfo")
    public UserInfoResponse getUserInfo(@NotNull Integer userId) {
        log.info("获取用户信息,userId:{}",userId);
        return userService.getUserInfo(userId);
    }

    @GetMapping("getAuthorInfo")
    public UserInfoResponse getAuthorInfo(@NotNull Integer blogid) {
        log.info("获取作者信息,博客id:{}",blogid);
        return userService.getAuthorInfo(blogid);
    }
}
