package com.example.springblogdemo.pojo.dataobject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {
    @TableId(value = "id" , type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String githubUrl;
    private Integer deleteFlag;
    private Date createTime;
    private Date updateTime;

}
