package com.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mybatisplus.model.UserInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserInfoMapperTest {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    void testQueryById() {
        UserInfo userInfo = userInfoMapper.selectById(2);
        System.out.println(userInfo);
    }

    @Test
    void testUpdate(){
        UserInfo userInfo = new UserInfo();
        userInfo.setId(2);
        userInfo.setUsername("admin111");
        int i = userInfoMapper.updateById(userInfo);
        System.out.println(i);
    }

    @Test
    void testInsert(){
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("zhaoliu11");
        userInfo.setPassword("123456");
        userInfo.setAge(12);
        int i = userInfoMapper.insert(userInfo);
        System.out.println(i);
    }

    @Test
    void testDelete(){
        int i = userInfoMapper.deleteById(2);
        System.out.println(i);
    }

    @Test
    void testQueryWrapper(){
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id","username","age").
                eq("age","18").like("username","min");
        List<UserInfo> userInfos = userInfoMapper.selectList(queryWrapper);
        System.out.println(userInfos);
    }
    
}