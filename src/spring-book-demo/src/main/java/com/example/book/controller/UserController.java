package com.example.book.controller;


import com.example.book.entity.Result;
import com.example.book.entity.UserInfo;
import com.example.book.interceptor.LoginInterceptor;
import com.example.book.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("login")
    public Result<UserInfo> login(String name , String password , HttpSession session){
        log.info("用户登录，接受到的参数 name:{}",name);
        if(!StringUtils.hasLength(name) || !StringUtils.hasLength(password)){
            return Result.fail(400, "用户名和密码不能为空");
        }

//        if(name.equals("admin") && password.equals("admin")){
//            session.setAttribute("name" , name);
//            return true;
//        }

        UserInfo userInfo = userService.getUserInfo(name,password);

        if(userInfo == null || !password.equals(userInfo.getPassword())){
            log.warn("用户密码验证错误, name:{}",name);
            return Result.fail(400, "账号或密码错误");
        }
        // 返回给前端之前先把密码抹掉，避免密码被带到浏览器
        userInfo.setPassword("");
        // 登录成功：把用户写进 session，LoginInterceptor 就是靠这个 key 判断有没有登录的
        session.setAttribute(LoginInterceptor.SESSION_USER_KEY , userInfo);
        return Result.success(userInfo);
    }
}
