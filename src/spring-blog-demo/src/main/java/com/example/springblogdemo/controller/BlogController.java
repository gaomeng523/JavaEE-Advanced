package com.example.springblogdemo.controller;

import com.example.springblogdemo.common.constants.Constant;
import com.example.springblogdemo.common.exception.BlogException;
import com.example.springblogdemo.pojo.request.AddBlogRequest;
import com.example.springblogdemo.pojo.request.UpdateBlogRequest;
import com.example.springblogdemo.pojo.response.BlogInfoResponse;
import com.example.springblogdemo.service.BlogService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 博客相关接口
 *
 * <p>类上的 {@code @Validated} 是必须的：只有它存在时，
 * 方法参数上的 {@code @NotNull} 这类约束才会被 Spring 校验，
 * 否则注解形同虚设。</p>
 */
@Slf4j
@Validated
@RestController
@RequestMapping("blog")
public class BlogController {
    private final BlogService blogService;
    public  BlogController(BlogService blogService) {
        this.blogService = blogService;
    }
    @GetMapping("getList")
    public List<BlogInfoResponse> getList() {
        return blogService.getList();
    }

    @GetMapping("getBlogDetail")
    public BlogInfoResponse getBlogDetail(
            @RequestParam("blogId") @NotNull(message = "blogId不能为空") Integer blogId) {
        log.info("查询博客详情，blogId:{}", blogId);
        return blogService.getBlogDetail(blogId);
    }

    /**
     * 统计某位作者的文章数
     *
     * <p>详情页左侧卡片需要展示「文章 N 篇」。放在后端用 count 查询完成，
     * 比前端拉全量列表自己过滤要准确得多，也不会把每篇博客的正文都传到浏览器。</p>
     */
    @GetMapping("countByUser")
    public Long countByUser(
            @RequestParam("userId") @NotNull(message = "userId不能为空") Integer userId) {
        log.info("统计用户文章数，userId:{}", userId);
        return blogService.countByUserId(userId);
    }

    /**
     * 新增博客
     *
     * <p>作者 id 通过 {@code @RequestAttribute} 从拦截器放入 request 域的值中取，
     * 不接收前端传参，从根本上杜绝伪造作者的风险。</p>
     */
    @PostMapping("addBlog")
    public Boolean addBlog(@Validated @RequestBody AddBlogRequest addBlogRequest,
                           @RequestAttribute(name = Constant.CURRENT_USER_ID, required = false) Integer userId) {
        log.info("新增博客，userId:{}, title:{}", userId, addBlogRequest.getTitle());
        return blogService.addBlog(addBlogRequest, requireLogin(userId));
    }

    @PostMapping("updateBlog")
    public Boolean updateBlog(@Validated @RequestBody UpdateBlogRequest updateBlogRequest,
                              @RequestAttribute(name = Constant.CURRENT_USER_ID, required = false) Integer userId) {
        log.info("更新博客，userId:{}, blogId:{}", userId, updateBlogRequest.getId());
        return blogService.updateBlog(updateBlogRequest, requireLogin(userId));
    }

    /**
     * 删除博客
     *
     * <p>虽然语义上删除用一个 blogId 参数也说得通，但这里统一用 POST + 参数，
     * 避免 GET 请求被浏览器预取或爬虫误触发。</p>
     */
    @PostMapping("deleteBlog")
    public Boolean deleteBlog(@RequestParam("blogId") @NotNull(message = "博客id不能为空") Integer blogId,
                              @RequestAttribute(name = Constant.CURRENT_USER_ID, required = false) Integer userId) {
        log.info("删除博客，userId:{}, blogId:{}", userId, blogId);
        return blogService.deleteBlog(blogId, requireLogin(userId));
    }

    /**
     * 兜底校验：确保当前请求确实是登录态。
     *
     * <p>正常情况下拦截器已经拦住了未登录请求，userId 一定不为空。
     * 但万一将来新增接口时漏配了拦截路径，这里能及时挡住，
     * 属于「防御性编程」——依赖链路上的安全措施不要单点。</p>
     */
    private Integer requireLogin(Integer userId) {
        if (userId == null) {
            log.warn("未登录用户尝试执行写操作");
            throw new BlogException("请先登录");
        }
        return userId;
    }
}
