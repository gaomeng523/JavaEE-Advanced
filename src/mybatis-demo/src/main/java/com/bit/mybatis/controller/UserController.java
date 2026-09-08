package com.bit.mybatis.controller;

import com.bit.mybatis.entity.UserInfo;
import com.bit.mybatis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getList")
    public List<UserInfo> getAllUser(){
        return userService.getAllUser();
    }

    @RequestMapping("/login")
    public Boolean login(String userName, String password){
        //参数校验
        if (!StringUtils.hasLength(userName) || !StringUtils.hasLength(password)){
            return false;
        }
        //密码验证
        List<UserInfo> userInfos = userService.selectUserByNameAndPassword(userName, password);
        if (userInfos.size()>0){
            return true;
        }
        return false;
    }



}
