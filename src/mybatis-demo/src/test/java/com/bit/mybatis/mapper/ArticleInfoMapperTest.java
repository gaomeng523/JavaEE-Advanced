package com.bit.mybatis.mapper;

import com.bit.mybatis.entity.ArticleInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleInfoMapperTest {

    @Autowired
    private ArticleInfoMapper articleInfoMapper;

    @Test
    void selectById() {
        ArticleInfo articleInfo = articleInfoMapper.selectById(1);
        System.out.println(articleInfo);
    }
}