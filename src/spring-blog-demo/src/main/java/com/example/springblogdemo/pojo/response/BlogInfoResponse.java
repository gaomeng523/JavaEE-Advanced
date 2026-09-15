package com.example.springblogdemo.pojo.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class BlogInfoResponse {
        @TableId(value = "id" , type = IdType.AUTO)
        private Integer id;
        private String title;
        private String content;
        private Integer userId;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        private Date updateTime;
}
