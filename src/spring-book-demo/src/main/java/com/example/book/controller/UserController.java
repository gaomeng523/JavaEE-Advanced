package com.example.book.controller;


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
    public Boolean login(String name , String password , HttpSession session){
        log.info("用户登录，接受到的参数 name:{}, ",name);
        if(!StringUtils.hasLength(name) || !StringUtils.hasLength(password)){
            return false;
        }

//        if(name.equals("admin") && password.equals("admin")){
//            session.setAttribute("name" , name);
//            return true;
//        }

        Boolean result = userService.checkPassword(name,password);
        return result;
    }
}
