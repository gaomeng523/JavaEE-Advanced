package com.example.book.mapper;

import com.example.book.entity.BookInfo;
import com.example.book.entity.PageRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BookMapper {
    @Insert("""
        insert into book_info (book_name, author, `count`, price, publish, status)
        value (#{bookName}, #{author}, #{count}, #{price}, #{publish}, #{status})
        """)
    Integer insertBook(BookInfo bookInfo);

    // 加了 order by 才能保证翻页结果稳定，否则 MySQL 返回顺序不固定，会出现重复行或漏行
    @Select("select * from book_info where status <> 0 order by id limit #{offset},#{pageSize}")
    List<BookInfo> getListByLimit(PageRequest pageRequest);

    @Select("select count(1) from book_info where status <> 0")
    Integer count();

    @Select("select * from book_info where id=#{id}")
    BookInfo queryBookById(Integer id);


    Integer updateBookById(BookInfo bookInfo);

    Integer batchDeleteByIds(@Param("ids") Integer[] ids);
}
