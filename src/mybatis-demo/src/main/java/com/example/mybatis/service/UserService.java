package com.example.mybatis.service;

import com.example.mybatis.entity.UserInfo;
import com.example.mybatis.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    public List<UserInfo> getAllUser(){
        return userInfoMapper.selectAll();
    }
}
