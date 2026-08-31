package com.example.springmvc;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("message")
@RestController
public class MessageController {
    private List<MessageInfo> messageInfoList = new ArrayList<>();
    @RequestMapping("publish")
    public Boolean publish(@RequestBody MessageInfo messageInfo){
        if(!StringUtils.hasLength(messageInfo.getFrom())
        || !StringUtils.hasLength(messageInfo.getTo())
        || !StringUtils.hasLength(messageInfo.getMessage())){
            return false;
        }
        messageInfoList.add(messageInfo);
        return true;
    }

    @RequestMapping("getList")
    public List<MessageInfo> getList(){
        return messageInfoList;
    }
}
