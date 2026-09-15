package com.example.springblogdemo.controller;

import com.example.springblogdemo.pojo.response.BlogInfoResponse;
import com.example.springblogdemo.service.BlogService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("blog")
public class BlogController {
    private final BlogService blogService;
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }
    @GetMapping("getList")
    public List<BlogInfoResponse> getList(){
         return blogService.getList();
    }

    @GetMapping("getBlogDetail")
    public BlogInfoResponse getBlogDetail(@NotNull(message = "blogId不能为空") Integer blogId){
        log.info("查询博客详情，blogId:{}",blogId);
        return blogService.getBlogDetal(blogId);
    }
}
