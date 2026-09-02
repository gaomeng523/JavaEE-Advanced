package com.example.book.dao;

import com.example.book.entity.BookInfo;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class BookDao {
    public List<BookInfo> mockData(){
        List<BookInfo> bookInfos = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            BookInfo book = new BookInfo();
            book.setId(i);
            book.setBookName("书籍" + i);
            book.setAuthor("作者" + i);
            book.setCount(i * 5 + 3);
            book.setPrice(new BigDecimal(new Random().nextInt(100)));
            book.setPublish("出版社" + i);
            book.setStatus(1);
            bookInfos.add(book);
        }
        return bookInfos;
    }
}
