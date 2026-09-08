package com.bit.mybatis.mapper;

import com.bit.mybatis.entity.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserInfoXmlMapper {
    List<UserInfo> selectAll();

    UserInfo selectById(Integer id);

    List<UserInfo> selectByAgeAndGender(Integer gender, Integer age);

    List<UserInfo> selectByAgeAndGender2(@Param("g") Integer gender, Integer age);


    Integer insertUser(UserInfo userInfo);


    Integer insertUser2(@Param("userInfo") UserInfo userInfo);


    Integer deleteUserById(Integer id);

    void updateUserById(Integer gender, Integer id);

    Integer insertByCondition(UserInfo userInfo);

    List<UserInfo> selectByCondition(UserInfo userInfo);

    Integer updateByCondition(UserInfo userInfo);

    Integer batchDelete(List<Integer> ids);
}
