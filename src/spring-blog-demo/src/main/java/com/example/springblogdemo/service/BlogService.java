package com.example.springblogdemo.service;

import com.example.springblogdemo.pojo.response.BlogInfoResponse;

import java.util.List;

public interface BlogService {
    List<BlogInfoResponse> getList();

    BlogInfoResponse getBlogDetal(Integer blogId);
}
