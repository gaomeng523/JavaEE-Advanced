package com.example.springblogdemo.controller;

import com.example.springblogdemo.common.constants.Constant;
import com.example.springblogdemo.common.exception.BlogException;
import com.example.springblogdemo.pojo.request.UserLoginRequest;
import com.example.springblogdemo.pojo.response.UserInfoResponse;
import com.example.springblogdemo.pojo.response.UserLoginResponse;
import com.example.springblogdemo.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关接口
 *
 * <p>类上的 {@code @Validated} 不能省，否则方法参数上的 {@code @NotNull} 不会被触发校验。</p>
 */
@Slf4j
@Validated
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    // 统一使用构造器注入：依赖明确、便于单元测试、也能避免字段注入的循环依赖问题
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public UserLoginResponse login(@Validated @RequestBody UserLoginRequest userLoginRequest) {
        log.info("用户登录，userName:{}", userLoginRequest.getUserName());
        return userService.login(userLoginRequest);
    }

    /**
     * 查询用户信息
     *
     * <p>从 token 里取当前登录人 id，而不是让前端传 —— 否则任何人改一下 userId
     * 就能查到别人的信息。</p>
     */
    @GetMapping("getUserInfo")
    public UserInfoResponse getUserInfo(
            @RequestAttribute(name = Constant.CURRENT_USER_ID, required = false) Integer userId) {
        if (userId == null) {
            throw new BlogException("请先登录");
        }
        log.info("获取当前登录用户信息，userId:{}", userId);
        return userService.getUserInfo(userId);
    }

    /**
     * 查询博客作者信息
     *
     * @param blogId 参数名统一为 blogId，与前端和其余接口保持一致
     */
    @GetMapping("getAuthorInfo")
    public UserInfoResponse getAuthorInfo(
            @RequestParam("blogId") @NotNull(message = "blogId不能为空") Integer blogId) {
        log.info("获取作者信息，blogId:{}", blogId);
        return userService.getAuthorInfo(blogId);
    }
}
