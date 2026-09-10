package com.example.springmvc.entity;


import lombok.Data;

import java.util.Date;

@Data
public class MessageInfo {
    private Integer id;
    private String from;
    private String to;
    private String message;
    private Date createTime;
    private Date updateTime;
}
