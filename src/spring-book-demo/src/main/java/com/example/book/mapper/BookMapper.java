package com.example.book.mapper;

import com.example.book.entity.BookInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper {
    @Insert("""
        insert into book_info (book_name, author, count, price, publish, status)
        value (#{bookName}, #{author}, #{count}, #{price}, #{publish}, #{status})
        """)
    Integer insertBook(BookInfo bookInfo);

}
