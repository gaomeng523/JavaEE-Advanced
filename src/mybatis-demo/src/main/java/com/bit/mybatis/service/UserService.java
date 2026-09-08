package com.bit.mybatis.service;

import com.bit.mybatis.entity.UserInfo;
import com.bit.mybatis.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    public List<UserInfo> getAllUser() {
        return userInfoMapper.selectAll();
    }

    public List<UserInfo> selectUserByNameAndPassword(String userName, String password) {
        return userInfoMapper.selectUserByNameAndPassword(userName, password);
    }
}
