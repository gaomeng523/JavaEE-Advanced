package com.example.book.mapper;

import com.example.book.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {
    @Select("SELECT * FROM user_info WHERE user_name=#{userName} and delete_flag=0")
    UserInfo queryUserById(String userName);
}
