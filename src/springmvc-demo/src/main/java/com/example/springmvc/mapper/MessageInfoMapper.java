package com.example.springmvc.mapper;

import com.example.springmvc.entity.MessageInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageInfoMapper {
    @Insert("insert into message_info (`from`, `to`, `message`) value (#{from}, #{to}, #{message})")
    Integer insertMessage(MessageInfo messageInfo);


    @Select("select * from message_info where delete_flag = 0")
    List<MessageInfo> quseryMessage();
}
