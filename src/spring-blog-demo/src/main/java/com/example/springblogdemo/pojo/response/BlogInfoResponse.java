package com.example.springblogdemo.pojo.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 博客列表 / 详情返回给前端的结构
 *
 * <p>注意这里<b>不要</b>保留 {@code @TableId} 等 MyBatis-Plus 注解 ——
 * 那是持久层实体的职责，响应对象只负责传输，带上数据库注解会造成分层污染。</p>
 */
@Data
public class BlogInfoResponse {
    private Integer id;
    private String title;
    private String content;
    private Integer userId;

    /** 格式化后输出给前端，避免前端拿到一长串时间戳还要自己转 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;
}
