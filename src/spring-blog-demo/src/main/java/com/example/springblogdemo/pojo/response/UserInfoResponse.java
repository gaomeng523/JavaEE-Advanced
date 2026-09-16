package com.example.springblogdemo.pojo.response;

import lombok.Data;


@Data
public class UserInfoResponse {
    private Integer id;
    private String userName;
    private String githubUrl;
}
