package com.example.book.controller;

import com.example.book.entity.BookInfo;
import com.example.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    private BookService bookService;
    @RequestMapping("getList")
    public List<BookInfo> getList(){
//        BookService bookService = new BookService();
        List<BookInfo> bookInfos = bookService.getList();
         return bookInfos;
    }

    @RequestMapping("addBook")
    public String addBook(BookInfo bookInfo){
        if(!StringUtils.hasLength(bookInfo.getBookName()) ||
            !StringUtils.hasLength(bookInfo.getAuthor()) ||
            bookInfo.getCount() == null ||
            bookInfo.getPrice() == null ||
            !StringUtils.hasLength(bookInfo.getPublish()) ||
            bookInfo.getStatus() == null){
                return "参数不合法";
        }

        Integer result = bookService.addBook(bookInfo);
        return result == 1 ? "":"插入图书失败";
    }
}
