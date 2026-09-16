package com.example.springblogdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springblogdemo.common.constants.Constant;
import com.example.springblogdemo.common.exception.BlogException;
import com.example.springblogdemo.common.utils.BeanTransfer;
import com.example.springblogdemo.common.utils.JwtUtil;
import com.example.springblogdemo.mapper.BlogMapper;
import com.example.springblogdemo.mapper.UserMapper;
import com.example.springblogdemo.pojo.dataobject.BlogInfo;
import com.example.springblogdemo.pojo.dataobject.UserInfo;
import com.example.springblogdemo.pojo.request.UserLoginRequest;
import com.example.springblogdemo.pojo.response.UserInfoResponse;
import com.example.springblogdemo.pojo.response.UserLoginResponse;
import com.example.springblogdemo.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        UserInfo userInfo = getUserInfoByName(userLoginRequest.getUserName());
        if (userInfo == null || userInfo.getId() == null) {
            throw new BlogException("用户不存在");
        }
        if(!userInfo.getPassword().equals(userLoginRequest.getPassword())) {
            throw new BlogException("密码不正确");
        }

        Map<String,Object> map = new HashMap<>();
        map.put("id" ,  userInfo.getId());
        map.put("username" ,  userInfo.getUserName());
        String s = JwtUtil.genJwt(map);
        return new UserLoginResponse(userInfo.getId(),s);
    }

    @Override
    public UserInfoResponse getUserInfo(Integer userId) {
        UserInfo userInfo = getUserInfoById(userId);
        if (userInfo == null || userInfo.getId() < 0) {
            throw new BlogException("用户不存在");
        }
        return BeanTransfer.trans(userInfo);
    }

    @Override
    public UserInfoResponse getAuthorInfo(Integer blogid) {
        BlogInfo blogInfo = getBlogInfo(blogid);
        if (blogInfo == null || blogInfo.getId() < 0) {
            throw new BlogException("博客不存在");
        }
        UserInfo userInfo = getUserInfoById(blogInfo.getUserId());
        if(userInfo == null ||  userInfo.getId() < 0){
            throw new BlogException("用户不存在");
        }
        return BeanTransfer.trans(userInfo);
    }

    public UserInfo getUserInfoByName(String userName){
        return userMapper.selectOne(new LambdaQueryWrapper<UserInfo>().
                eq(UserInfo::getUserName, userName)
                .eq(UserInfo::getDeleteFlag, Constant.UNDELETE)
        );
    }
    public UserInfo getUserInfoById(Integer userId) {
        return userMapper.selectOne(new LambdaQueryWrapper<UserInfo>().
                eq(UserInfo::getId, userId)
                .eq(UserInfo::getDeleteFlag, Constant.UNDELETE)
        );
    }

    public BlogInfo getBlogInfo(Integer blogId) {
        return blogMapper.selectOne(new LambdaQueryWrapper<BlogInfo>()
                .eq(BlogInfo::getDeleteFlag , Constant.UNDELETE)
                .eq(BlogInfo::getId,blogId));
    }
}
