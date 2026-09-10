package com.example.book.service;

import com.example.book.dao.BookDao;
import com.example.book.entity.BookInfo;
import com.example.book.mapper.BookMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookDao bookDao;
    public List<BookInfo> getList(){
//        BookDao bookDao = new BookDao();
        List<BookInfo> bookInfos = bookDao.mockData();
        for (BookInfo bookInfo: bookInfos) {
            if(bookInfo.getStatus() == 1){
                bookInfo.setStatusCN("可借阅");
            }else {
                bookInfo.setStatusCN("不可借阅");
            }
        }
        return bookInfos;
    }

    public Integer addBook(BookInfo bookInfo) {
        Integer result = null;
        try {
            result =  bookMapper.insertBook(bookInfo);
        }catch (Exception e){
            log.error("图书插入发生异常，e",e);
        }
        return result;
    }
}
