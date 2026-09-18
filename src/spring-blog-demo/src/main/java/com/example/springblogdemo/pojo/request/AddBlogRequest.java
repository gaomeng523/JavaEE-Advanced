package com.example.springblogdemo.pojo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 新增博客的请求参数
 *
 * <p>注意这里<b>不包含 userId</b>：作者应该由服务端从 token 中解析，
 * 如果让前端传，用户就能伪造成别人的 id 发博客。</p>
 */
@Data
public class AddBlogRequest {
    @NotBlank(message = "博客标题不能为空")
    @Length(max = 100, message = "标题长度不能超过100个字符")
    private String title;

    @NotBlank(message = "博客内容不能为空")
    private String content;
}
