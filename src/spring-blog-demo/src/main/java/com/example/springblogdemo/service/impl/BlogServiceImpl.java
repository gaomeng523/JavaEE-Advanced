package com.example.springblogdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springblogdemo.common.constants.Constant;
import com.example.springblogdemo.common.exception.BlogException;
import com.example.springblogdemo.common.utils.BeanTransfer;
import com.example.springblogdemo.mapper.BlogMapper;
import com.example.springblogdemo.pojo.dataobject.BlogInfo;
import com.example.springblogdemo.pojo.request.AddBlogRequest;
import com.example.springblogdemo.pojo.request.UpdateBlogRequest;
import com.example.springblogdemo.pojo.response.BlogInfoResponse;
import com.example.springblogdemo.service.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("blogService")
public class BlogServiceImpl implements BlogService {
    private final BlogMapper blogMapper;

    public BlogServiceImpl(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    @Override
    public List<BlogInfoResponse> getList() {
        List<BlogInfo> blogInfos = blogMapper.selectList(new LambdaQueryWrapper<BlogInfo>()
                .orderByDesc(BlogInfo::getId));

        return blogInfos.stream()
                .map(BeanTransfer::trans)
                .collect(Collectors.toList());
    }

    @Override
    public BlogInfoResponse getBlogDetail(Integer blogId) {
        BlogInfo blogInfo = getBlogInfo(blogId);
        // 查不到要明确抛业务异常，否则 BeanTransfer.trans(null) 会返回 null
        // 上游如果直接取属性就会 NPE，错误信息也完全不友好
        if (blogInfo == null) {
            log.warn("博客不存在，blogId={}", blogId);
            throw new BlogException("博客不存在");
        }
        return BeanTransfer.trans(blogInfo);
    }

    /**
     * 新增博客
     *
     * @param addBlogRequest 标题与内容
     * @param userId         当前登录用户 id（由 Controller 从 token 解析后传入）
     */
    @Override
    public Boolean addBlog(AddBlogRequest addBlogRequest, Integer userId) {
        BlogInfo blogInfo = BeanTransfer.trans(addBlogRequest);
        blogInfo.setUserId(userId);
        blogInfo.setDeleteFlag(Constant.UNDELETE);
        // createTime / updateTime 由 MyMetaObjectHandler 自动填充，无需手动设置

        int rows = blogMapper.insert(blogInfo);
        if (rows != 1) {
            log.error("新增博客失败，影响行数={}, userId={}", rows, userId);
            throw new BlogException("博客发表失败，请稍后重试");
        }
        return true;
    }

    /**
     * 更新博客
     *
     * <p>只允许作者本人修改自己的博客，所以需要校验归属关系。</p>
     */
    @Override
    public Boolean updateBlog(UpdateBlogRequest updateBlogRequest, Integer userId) {
        BlogInfo exist = getBlogInfo(updateBlogRequest.getId());
        if (exist == null) {
            throw new BlogException("博客不存在");
        }
        // 越权校验：别人的博客不允许改
        if (!exist.getUserId().equals(userId)) {
            log.warn("越权更新被拒绝：blogId={}, 作者={}, 当前用户={}",
                    updateBlogRequest.getId(), exist.getUserId(), userId);
            throw new BlogException("无权操作他人的博客");
        }

        BlogInfo blogInfo = BeanTransfer.trans(updateBlogRequest);
        // userId 不参与更新，避免前端把作者改掉
        blogInfo.setUserId(null);

        // 标题和内容都为空说明是个没有任何改动的请求，直接返回成功即可
        if (!StringUtils.hasText(blogInfo.getTitle())
                && !StringUtils.hasText(blogInfo.getContent())) {
            log.warn("更新博客请求没有携带任何可修改字段，blogId={}", updateBlogRequest.getId());
            return true;
        }

        // updateById 只会更新非 null 字段，所以传 null 的字段不会被清空
        blogMapper.updateById(blogInfo);

        // 注意：这里不能判断 rows != 1 就报错。
        // MySQL 在「新值和旧值完全相同」时受影响行数是 0，但这不是失败，
        // 用户点了更新但没改动内容是很正常的情况。
        return true;
    }

    /**
     * 删除博客（逻辑删除）
     *
     * <p>不物理删除数据，只把 delete_flag 置为 1。实体上的 {@code @TableLogic}
     * 会让 {@code deleteById} 自动生成 update 语句而不是 delete。</p>
     */
    @Override
    public Boolean deleteBlog(Integer blogId, Integer userId) {
        BlogInfo exist = getBlogInfo(blogId);
        if (exist == null) {
            throw new BlogException("博客不存在");
        }
        if (!exist.getUserId().equals(userId)) {
            log.warn("越权删除被拒绝：blogId={}, 作者={}, 当前用户={}",
                    blogId, exist.getUserId(), userId);
            throw new BlogException("无权操作他人的博客");
        }

        int rows = blogMapper.deleteById(blogId);
        if (rows != 1) {
            log.error("删除博客失败，影响行数={}, blogId={}", rows, blogId);
            throw new BlogException("博客删除失败，请稍后重试");
        }
        return true;
    }

    /**
     * 统计某个作者发布的博客数量
     *
     * <p>用 {@code selectCount} 让数据库来数，只返回一个数字，
     * 不会像 {@code selectList} 那样把每篇博客的完整 content 都拉到内存里。</p>
     */
    @Override
    public Long countByUserId(Integer userId) {
        if (userId == null) {
            return 0L;
        }
        // delete_flag 的过滤依旧由 @TableLogic 自动拼接，这里不用手写
        return blogMapper.selectCount(new LambdaQueryWrapper<BlogInfo>()
                .eq(BlogInfo::getUserId, userId));
    }

    /**
     * 查询未删除的博客
     *
     * <p>delete_flag 的过滤条件由实体上的 {@code @TableLogic} 自动拼接，
     * 这里不需要再手动写 eq，否则 SQL 里会出现两次相同条件。</p>
     */
    public BlogInfo getBlogInfo(Integer blogId) {
        return blogMapper.selectOne(new LambdaQueryWrapper<BlogInfo>()
                .eq(BlogInfo::getId, blogId));
    }
}
