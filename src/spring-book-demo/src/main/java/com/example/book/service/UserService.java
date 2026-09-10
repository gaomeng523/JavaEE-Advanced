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
    public Boolean checkPassword(String name, String password) {
        UserInfo userInfo = userInfoMapper.queryUserById(name);
        if(userInfo == null || !password.equals(userInfo.getPassword())){
            log.warn("用户密码验证错误, name:{}",name);
            return false;
        }
        return true;
    }
}
