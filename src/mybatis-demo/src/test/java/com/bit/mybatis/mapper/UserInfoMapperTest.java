package com.bit.mybatis.mapper;

import com.bit.mybatis.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class UserInfoMapperTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    void selectAll() {
        List<UserInfo> userInfos = userInfoMapper.selectAll();
        userInfos.forEach(System.out::println);
    }
    @Test
    void selectById() {
        UserInfo userInfo = userInfoMapper.selectById(4);
        System.out.println(userInfo);
    }

    @Test
    void selectByAgeAndGender() {
        UserInfo userInfo = userInfoMapper.selectByAgeAndGender(0,18);
        System.out.println(userInfo);
    }

    @Test
    void insertUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("java1");
        userInfo.setPassword("java1");
        userInfo.setAge(1);
//        userInfo.setGender(1);
        Integer result = userInfoMapper.insertUser(userInfo);
//        log.info("影响行数:"+result + ", 自增ID:" + userInfo.getId());
        log.info("影响行数:{}, 自增ID:{}", result, userInfo.getId());
    }

    @Test
    void insertUser2() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("java2");
        userInfo.setPassword("java2");
        userInfo.setAge(2);
        userInfo.setGender(2);
        Integer result = userInfoMapper.insertUser2(userInfo);
        log.info("影响行数:"+result);
    }

    @Test
    void deleteUserById() {
        Integer result = userInfoMapper.deleteUserById(8);
        log.info("影响行数:"+ result);
    }

    @Test
    void updateUserById() {
        userInfoMapper.updateUserById(0,null);
    }

    @Test
    void selectUserByName() {
        List<UserInfo> userInfos = userInfoMapper.selectUserByName("' or 1='1");
        userInfos.forEach(System.out::println);
    }

    @Test
    void selectUserByOrder() {
        userInfoMapper.selectUserByOrder("asc").forEach(System.out::println);
    }

    @Test
    void selectUserByLikeUserName() {
        userInfoMapper.selectUserByLikeUserName("java").forEach(System.out::println);
    }

    @Test
    void insertByCondition() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("java1");
        userInfo.setPassword("java1");
//        userInfo.setAge(1);
        Integer result = userInfoMapper.insertByCondition(userInfo);
        log.info("影响行数:{}", result);
    }
}