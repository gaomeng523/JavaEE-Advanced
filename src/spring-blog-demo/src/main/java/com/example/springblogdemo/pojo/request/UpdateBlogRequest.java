package com.example.springblogdemo.pojo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 更新博客的请求参数
 */
@Data
public class UpdateBlogRequest {
    @NotNull(message = "博客id不能为空")
    private Integer id;
    private String title;
    private String content;
}
