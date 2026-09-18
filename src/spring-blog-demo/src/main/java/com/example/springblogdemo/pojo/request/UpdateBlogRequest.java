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

    // 刻意不加 @NotBlank 和 @NotNull：
    // 更新博客允许「只改内容不改标题」，所以两个字段都可以为 null，
    // 由 Service 判断是否所有字段都为空，都为空就直接返回。
    private String title;

    private String content;
}
