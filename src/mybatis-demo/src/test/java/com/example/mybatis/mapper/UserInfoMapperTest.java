package com.example.mybatis.mapper;

import com.example.mybatis.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.objenesis.SpringObjenesis;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserInfoMapperTest {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Test
    void selectAll() {
        List<UserInfo> userInfos = userInfoMapper.selectAll();
        userInfos.forEach(System.out::println);
    }
}