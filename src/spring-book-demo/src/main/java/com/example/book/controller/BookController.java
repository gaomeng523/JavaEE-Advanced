package com.example.book.controller;

import com.example.book.entity.BookInfo;
import com.example.book.service.BookService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("book")
public class BookController {
    @RequestMapping("getList")
    public List<BookInfo> getList(){
        BookService bookService = new BookService();
        List<BookInfo> bookInfos = bookService.getList();
         return bookInfos;
    }
}
