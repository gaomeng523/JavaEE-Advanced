package com.bit.mybatis.mapper;

import com.bit.mybatis.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class UserInfoXmlMapperTest {

    @Autowired
    private UserInfoXmlMapper userInfoXmlMapper;

//    @Autowired
//    public UserInfoXmlMapperTest(UserInfoXmlMapper userInfoXmlMapper) {
//        this.userInfoXmlMapper = userInfoXmlMapper;
//    }

    @Test
    void selectAll() {
        userInfoXmlMapper.selectAll().forEach(System.out::println);
    }

    @Test
    void selectById() {
        System.out.println(userInfoXmlMapper.selectById(1));
    }

    @Test
    void selectByAgeAndGender() {
        List<UserInfo> userInfos = userInfoXmlMapper.selectByAgeAndGender(1, 1);
        userInfos.forEach(System.out::println);

    }
    @Test
    void selectByAgeAndGender2() {
        List<UserInfo> userInfos = userInfoXmlMapper.selectByAgeAndGender2(1, 1);
        userInfos.forEach(System.out::println);

    }

    @Test
    void insertUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("java1");
        userInfo.setPassword("java1");
        userInfo.setAge(1);
        userInfo.setGender(1);
        Integer result = userInfoXmlMapper.insertUser(userInfo);
        log.info("影响行数:{}, 自增ID:{}", result, userInfo.getId());
    }

    @Test
    void insertUser2() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("java1");
        userInfo.setPassword("java1");
        userInfo.setAge(1);
        userInfo.setGender(1);
        Integer result = userInfoXmlMapper.insertUser2(userInfo);
        log.info("影响行数:{}", result);
    }

    @Test
    void deleteUserById() {
        userInfoXmlMapper.deleteUserById(14);
    }

    @Test
    void updateUserById() {
        userInfoXmlMapper.updateUserById(2, 13);
    }

    @Test
    void insertByCondition() {
        UserInfo userInfo = new UserInfo();
//        userInfo.setUsername("java1");
        userInfo.setPassword("java1");
        userInfo.setAge(1);
//        userInfo.setGender(1);
        Integer result = userInfoXmlMapper.insertByCondition(userInfo);
        log.info("影响行数:{}", result);
    }

    @Test
    void selectByCondition() {
        UserInfo userInfo = new UserInfo();
//        userInfo.setAge(1);
        userInfo.setGender(1);
        List<UserInfo> userInfos = userInfoXmlMapper.selectByCondition(userInfo);
        userInfos.forEach(System.out::println);
    }

    @Test
    void updateByCondition() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(20);
//        userInfo.setUsername("update111");
//        userInfo.setAge(1);
//        userInfo.setGender(1);
        userInfoXmlMapper.updateByCondition(userInfo);
    }

    @Test
    void batchDelete() {
        userInfoXmlMapper.batchDelete(List.of(18,19,20));
    }
}