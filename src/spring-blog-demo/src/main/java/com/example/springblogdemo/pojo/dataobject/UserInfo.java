package com.example.springblogdemo.pojo.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfo {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String userName;
    private String password;
    private String githubUrl;
    @TableLogic(value = "0", delval = "1")
    private Integer deleteFlag;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
}
