package com.example.springblogdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springblogdemo.common.constants.Constant;
import com.example.springblogdemo.common.exception.BlogException;
import com.example.springblogdemo.common.utils.BeanTransfer;
import com.example.springblogdemo.common.utils.JwtUtil;
import com.example.springblogdemo.common.utils.Md5Util;
import com.example.springblogdemo.mapper.BlogMapper;
import com.example.springblogdemo.mapper.UserMapper;
import com.example.springblogdemo.pojo.dataobject.BlogInfo;
import com.example.springblogdemo.pojo.dataobject.UserInfo;
import com.example.springblogdemo.pojo.request.UserLoginRequest;
import com.example.springblogdemo.pojo.response.UserInfoResponse;
import com.example.springblogdemo.pojo.response.UserLoginResponse;
import com.example.springblogdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final BlogMapper blogMapper;
    private final JwtUtil jwtUtil;

    // 三个依赖都用构造器注入，替代原来的 @Autowired 字段注入
    public UserServiceImpl(UserMapper userMapper, BlogMapper blogMapper, JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.blogMapper = blogMapper;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 登录
     *
     * <p>安全提醒：用户不存在和密码错误应返回同样的提示语，否则攻击者可以借此
     * 枚举出哪些用户名是真实存在的。这里为便于学习调试仍分开提示，实际项目建议合并。</p>
     */
    @Override
    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        UserInfo userInfo = getUserInfoByName(userLoginRequest.getUserName());
        if (userInfo == null) {
            throw new BlogException("用户名或密码错误");
        }

        // 加盐 MD5 校验：数据库中存的是「32位密文 + 32位盐」
        if (!Md5Util.verify(userLoginRequest.getPassword(), userInfo.getPassword())) {
            throw new BlogException("用户名或密码错误");
        }

        Map<String, Object> map = new HashMap<>();
        map.put(Constant.JWT_CLAIM_ID, userInfo.getId());
        map.put(Constant.JWT_CLAIM_USERNAME, userInfo.getUserName());
        String token = jwtUtil.genJwt(map);

        log.info("登录成功，userId:{}", userInfo.getId());
        return new UserLoginResponse(userInfo.getId(), token);
    }

    @Override
    public UserInfoResponse getUserInfo(Integer userId) {
        UserInfo userInfo = getUserInfoById(userId);
        // 注意：这里判断的是 == null。
        // 原先写的 getId() < 0 是无效判断 —— 自增主键不可能是负数，等价于没写
        if (userInfo == null) {
            throw new BlogException("用户不存在");
        }
        return BeanTransfer.trans(userInfo);
    }

    @Override
    public UserInfoResponse getAuthorInfo(Integer blogId) {
        BlogInfo blogInfo = getBlogInfo(blogId);
        if (blogInfo == null) {
            throw new BlogException("博客不存在");
        }

        UserInfo userInfo = getUserInfoById(blogInfo.getUserId());
        if (userInfo == null) {
            throw new BlogException("用户不存在");
        }
        return BeanTransfer.trans(userInfo);
    }

    public UserInfo getUserInfoByName(String userName) {
        // delete_flag 过滤由实体上的 @TableLogic 自动拼接，这里显式写会重复
        return userMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getUserName, userName));
    }

    public UserInfo getUserInfoById(Integer userId) {
        // selectOne 在结果超过一条时会抛异常，精确主键查询能保证唯一性
        return userMapper.selectOne(new LambdaQueryWrapper<UserInfo>()
                .eq(UserInfo::getId, userId));
    }

    public BlogInfo getBlogInfo(Integer blogId) {
        return blogMapper.selectOne(new LambdaQueryWrapper<BlogInfo>()
                .eq(BlogInfo::getId, blogId));
    }
}
