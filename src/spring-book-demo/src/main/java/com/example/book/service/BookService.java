package com.example.book.service;

import com.example.book.dao.BookDao;
import com.example.book.entity.BookInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
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
}
