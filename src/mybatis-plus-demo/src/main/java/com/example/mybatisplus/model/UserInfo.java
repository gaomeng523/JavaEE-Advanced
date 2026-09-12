package com.example.mybatisplus.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("userinfo")
@Data
public class UserInfo {
    private Integer id;
    private String username;
    private String password;
    private Integer age;
    private String phone;
    private Integer deleteFlag;
    private Date createTime;
    private Date updateTime;
}
