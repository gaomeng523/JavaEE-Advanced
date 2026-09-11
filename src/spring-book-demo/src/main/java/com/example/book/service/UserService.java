package com.example.book.service;

import com.example.book.entity.UserInfo;
import com.example.book.mapper.UserInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    public UserInfo getUserInfo(String name, String password) {
        UserInfo userInfo = userInfoMapper.queryUserById(name);
        return userInfo;
    }
}
