package com.bit.mybatis.entity;

import lombok.Data;
import java.util.Date;

@Data
public class ArticleInfo {
    private Integer id;
    private String title;
    private String content;
    private Integer uid;
    private Integer deleteFlag;
    private Date createTime;
    private Date updateTime;

    //userInfo
    private String username;
    private String password;
}
