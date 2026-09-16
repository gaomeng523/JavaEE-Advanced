package com.example.springblogdemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springblogdemo.common.constants.Constant;
import com.example.springblogdemo.common.utils.BeanTransfer;
import com.example.springblogdemo.mapper.BlogMapper;
import com.example.springblogdemo.pojo.dataobject.BlogInfo;
import com.example.springblogdemo.pojo.response.BlogInfoResponse;
import com.example.springblogdemo.service.BlogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("blogService")
public class BlogServiceImpl implements BlogService {
    private final BlogMapper blogMapper;

    public BlogServiceImpl(BlogMapper blogMapper) {
        this.blogMapper = blogMapper;
    }

    @Override
    public List<BlogInfoResponse> getList() {

        List<BlogInfo> blogInfos = blogMapper.selectList(new LambdaQueryWrapper<BlogInfo>()
                .eq(BlogInfo::getDeleteFlag, Constant.UNDELETE)
                .orderByDesc(BlogInfo::getId));

       List<BlogInfoResponse> blogInfoResponses = blogInfos.stream().map(blogInfo ->
               BeanTransfer.trans(blogInfo)).collect(Collectors.toList());

        return blogInfoResponses;
    }

    @Override
    public BlogInfoResponse getBlogDetal(Integer blogId) {
        BlogInfo blogInfo = getBlogInfo(blogId);
        return BeanTransfer.trans(blogInfo);
    }

    public BlogInfo getBlogInfo(Integer blogId) {
        return blogMapper.selectOne(new LambdaQueryWrapper<BlogInfo>()
                .eq(BlogInfo::getDeleteFlag , Constant.UNDELETE)
                .eq(BlogInfo::getId,blogId));
    }
}
