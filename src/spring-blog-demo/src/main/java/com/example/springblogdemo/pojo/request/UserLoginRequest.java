package com.example.springblogdemo.pojo.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserLoginRequest {
    @NotNull(message = "用户名不能为空")
    @Length(max = 20 , message = "不能超过20")
    private String userName;
    @NotNull(message = "密码不能为空")
    private String password;
}
