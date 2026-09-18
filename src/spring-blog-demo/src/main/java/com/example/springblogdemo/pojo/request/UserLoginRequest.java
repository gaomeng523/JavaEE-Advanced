package com.example.springblogdemo.pojo.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 用户登录请求参数
 */
@Data
public class UserLoginRequest {

    // 用 @NotBlank 而不是 @NotNull：前者会顺带拦住空字符串和纯空格，
    // 后者对 "" 是放行的，前端不填只提交空串就能绕过校验
    @NotBlank(message = "用户名不能为空")
    @Length(max = 20, message = "用户名长度不能超过20")
    private String userName;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 32, message = "密码长度需在6~32位之间")
    private String password;
}
