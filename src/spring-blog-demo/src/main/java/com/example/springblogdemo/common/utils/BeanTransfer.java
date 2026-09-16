package com.example.springblogdemo.common.utils;

import com.example.springblogdemo.pojo.dataobject.BlogInfo;
import com.example.springblogdemo.pojo.dataobject.UserInfo;
import com.example.springblogdemo.pojo.response.BlogInfoResponse;
import com.example.springblogdemo.pojo.response.UserInfoResponse;
import org.springframework.beans.BeanUtils;

public class BeanTransfer {
    public static BlogInfoResponse trans(BlogInfo blogInfo){
        BlogInfoResponse blogInfoResponse = new BlogInfoResponse();
        BeanUtils.copyProperties(blogInfo,blogInfoResponse);
        return blogInfoResponse;
    }

    public static UserInfoResponse trans(UserInfo userInfo){
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        BeanUtils.copyProperties(userInfo,userInfoResponse);
        return userInfoResponse;
    }
}
