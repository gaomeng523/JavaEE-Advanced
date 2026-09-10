
package com.example.springmvc.service;

import com.example.springmvc.entity.MessageInfo;
import com.example.springmvc.mapper.MessageInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageInfoService {

    @Autowired
    private MessageInfoMapper messageInfoMapper;
    public void addMessage(MessageInfo messageInfo) {
        messageInfoMapper.insertMessage(messageInfo);
    }

    public List<MessageInfo> queryAllMessage() {
        return messageInfoMapper.quseryMessage();
    }
}
