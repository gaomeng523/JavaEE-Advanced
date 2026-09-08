package com.bit.mybatis.mapper;

import com.bit.mybatis.entity.ArticleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ArticleInfoMapper {
    @Select("""
        select ta.*, tb.username, tb.`password` from article_info ta
        left join user_info tb on ta.uid = tb.id
        where ta.id = #{id}
        """)
    ArticleInfo selectById(Integer id);
}
