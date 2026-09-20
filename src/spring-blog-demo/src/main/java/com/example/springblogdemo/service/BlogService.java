package com.example.springblogdemo.service;

import com.example.springblogdemo.pojo.request.AddBlogRequest;
import com.example.springblogdemo.pojo.request.UpdateBlogRequest;
import com.example.springblogdemo.pojo.response.BlogInfoResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlogService {
    List<BlogInfoResponse> getList();

    BlogInfoResponse getBlogDetail(Integer blogId);
    /**
     * 统计某个作者发布的博客数量
     *
     * <p>详情页左侧卡片要显示「文章 N 篇」，如果让前端拉全量列表自己数，
     * 每次打开详情页都会多一次全表查询。交给数据库 count 更准确也更省。</p>
     *
     * @param userId 作者的用户 id
     * @return 该作者未删除的博客数
     */
    Long countByUserId(Integer userId);
    /**
     * 新增博客
     *
     * @param userId 当前登录用户 id，由 Controller 从 token 中解析得到
     */
    Boolean addBlog(AddBlogRequest addBlogRequest, Integer userId);
    /**
     * 更新博客
     *
     * @param userId 当前登录用户 id，用于校验是否为本人的博客
     */
    Boolean updateBlog(UpdateBlogRequest updateBlogRequest, Integer userId);
    /**
     * 删除博客（逻辑删除）
     *
     * @param userId 当前登录用户 id，用于校验是否为本人的博客
     */
    Boolean deleteBlog(Integer blogId, Integer userId);
}
