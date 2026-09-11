package com.example.book.mapper;

import com.example.book.entity.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserInfoMapperTest {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Test
    void queryUserById() {
        UserInfo userInfo = userInfoMapper.queryUserById("admin");
        System.out.println(userInfo);
    }
}