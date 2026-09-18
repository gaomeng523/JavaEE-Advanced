package com.example.springblogdemo.common.utils;

import com.example.springblogdemo.pojo.dataobject.BlogInfo;
import com.example.springblogdemo.pojo.dataobject.UserInfo;
import com.example.springblogdemo.pojo.request.AddBlogRequest;
import com.example.springblogdemo.pojo.request.UpdateBlogRequest;
import com.example.springblogdemo.pojo.response.BlogInfoResponse;
import com.example.springblogdemo.pojo.response.UserInfoResponse;
import org.springframework.beans.BeanUtils;

/**
 * POJO 之间的属性拷贝工具
 *
 * <p>统一收口转换逻辑，避免各处散落 {@code BeanUtils.copyProperties} 调用。
 * 注意：{@code BeanUtils} 是「按属性名拷贝」，字段名对不上就不会拷过去。</p>
 */
public class BeanTransfer {

    private BeanTransfer() {
        // 工具类不需要实例化
    }

    public static BlogInfoResponse trans(BlogInfo blogInfo) {
        if (blogInfo == null) {
            return null;
        }
        BlogInfoResponse blogInfoResponse = new BlogInfoResponse();
        BeanUtils.copyProperties(blogInfo, blogInfoResponse);
        return blogInfoResponse;
    }

    public static UserInfoResponse trans(UserInfo userInfo) {
        if (userInfo == null) {
            return null;
        }
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        BeanUtils.copyProperties(userInfo, userInfoResponse);
        return userInfoResponse;
    }

    /**
     * 新增请求 → 实体
     *
     * <p>这里只拷贝 title / content，userId 由 Service 从当前登录人那里补上，
     * createTime / updateTime 交给 MyBatis-Plus 自动填充。</p>
     */
    public static BlogInfo trans(AddBlogRequest addBlogRequest) {
        if (addBlogRequest == null) {
            return null;
        }
        BlogInfo blogInfo = new BlogInfo();
        BeanUtils.copyProperties(addBlogRequest, blogInfo);
        return blogInfo;
    }

    /**
     * 更新请求 → 实体
     *
     * <p>只拷贝会变的字段，id 用于定位记录，其余字段保持 null 让 MyBatis-Plus
     * 的 updateById 只更新非空字段（动态 SQL 特性）。</p>
     */
    public static BlogInfo trans(UpdateBlogRequest updateBlogRequest) {
        if (updateBlogRequest == null) {
            return null;
        }
        BlogInfo blogInfo = new BlogInfo();
        BeanUtils.copyProperties(updateBlogRequest, blogInfo);
        return blogInfo;
    }
}
