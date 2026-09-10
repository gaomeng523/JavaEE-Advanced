package com.example.springmvc.mapper;

import com.example.springmvc.entity.MessageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessageInfoMapperTest {

    @Autowired
    private MessageInfoMapper messageInfoMapper;
    @Test
    void insertMessage() {
        MessageInfo messageInfo = new MessageInfo();
        messageInfo.setFrom("aa");
        messageInfo.setTo("bb");
        messageInfo.setMessage("cc");
        messageInfoMapper.insertMessage(messageInfo);
    }

    @Test
    void quseryMessage() {
        List<MessageInfo> messageInfos = messageInfoMapper.quseryMessage();
        System.out.println(messageInfos);
    }
}