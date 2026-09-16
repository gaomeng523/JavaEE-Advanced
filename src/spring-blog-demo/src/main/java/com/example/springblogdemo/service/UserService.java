package com.example.springblogdemo.service;

import com.example.springblogdemo.pojo.request.UserLoginRequest;
import com.example.springblogdemo.pojo.response.UserInfoResponse;
import com.example.springblogdemo.pojo.response.UserLoginResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserLoginResponse login(UserLoginRequest userLoginRequest);

    UserInfoResponse getUserInfo(@NotNull Integer userId);

    UserInfoResponse getAuthorInfo(@NotNull Integer blogid);
}
