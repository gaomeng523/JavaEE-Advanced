package com.example.book.service;

import com.example.book.dao.BookDao;
import com.example.book.entity.BookInfo;
import com.example.book.entity.PageRequest;
import com.example.book.entity.PageResult;
import com.example.book.enums.BookStatus;
import com.example.book.mapper.BookMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
            log.error("图书插入发生异常，bookName:{}", bookInfo.getBookName(), e);
        }
        return result;
    }

    public PageResult<BookInfo> getListByPage(PageRequest pageRequest) {
        int count = bookMapper.count();
        List<BookInfo> bookInfos = bookMapper.getListByLimit(pageRequest);

        for (BookInfo bookInfo: bookInfos) {
            bookInfo.setStatusCN(BookStatus.getStatusByCode(bookInfo.getStatus()));
        }

        PageResult<BookInfo> result = new PageResult<>(bookInfos, count, pageRequest);
        return result;
    }

    public BookInfo queryBookById(Integer bookId) {
        return bookMapper.queryBookById(bookId);
    }

    public Integer updateBook(BookInfo bookInfo) {
        try {
            return bookMapper.updateBookById(bookInfo);
        }catch (Exception e){
            log.error("图书更新发生异常，bookId:{}",bookInfo.getId(),e);
            return null;
        }
    }

    public Integer batchDeleteBook(Integer[] ids) {
        try {
            return bookMapper.batchDeleteByIds(ids);
        }catch (Exception e){
            log.error("批量删除图书发生异常，ids:{}", Arrays.toString(ids),e);
            return null;
        }
    }
}
