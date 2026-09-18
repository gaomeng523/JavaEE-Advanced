package com.example.springblogdemo.service;

import com.example.springblogdemo.pojo.request.UserLoginRequest;
import com.example.springblogdemo.pojo.response.UserInfoResponse;
import com.example.springblogdemo.pojo.response.UserLoginResponse;

/**
 * 用户服务接口
 *
 * <p>接口上不挂 {@code @Service}（那是实现类的职责），
 * 参数上也不写校验注解（校验发生在 Controller 入口，Service 之间互相调用时不受 MVC 校验管辖）。</p>
 */
public interface UserService {
    UserLoginResponse login(UserLoginRequest userLoginRequest);

    UserInfoResponse getUserInfo(Integer userId);

    UserInfoResponse getAuthorInfo(Integer blogId);
}
