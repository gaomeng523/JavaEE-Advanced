package com.example.springaopdemo.controller;

import com.example.springaopdemo.common.Result;
import com.example.springaopdemo.model.BookInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AOP 快速入门的目标类
 * <p>
 * 需求：统计图书系统各个接口方法的执行时间（由 {@code aspect.TimeAspect} 完成）
 * <p>
 * 这里用内存 Map 模拟数据库，方便直接跑起来看到耗时日志。
 */
@Slf4j
@RequestMapping("/book")
@RestController
public class BookController {

    /** 模拟数据库 */
    private final Map<Integer, BookInfo> bookMap = new ConcurrentHashMap<>();

    private final AtomicInteger idGenerator = new AtomicInteger(0);

    /**
     * 新增图书
     * 访问：http://127.0.0.1:8080/book/addBook?bookName=JavaEE&author=张三&price=59.9&status=1
     */
    @RequestMapping("/addBook")
    public Result addBook(BookInfo bookInfo) {
        int bookId = idGenerator.incrementAndGet();
        bookInfo.setBookId(bookId);
        bookMap.put(bookId, bookInfo);
        log.info("新增图书成功：{}", bookInfo);
        return Result.success(bookInfo);
    }

    /**
     * 根据 ID 查询图书
     * 访问：http://127.0.0.1:8080/book/queryBookById?bookId=1
     */
    @RequestMapping("/queryBookById")
    public BookInfo queryBookById(Integer bookId) {
        BookInfo bookInfo = bookMap.get(bookId);
        log.info("查询图书：{} -> {}", bookId, bookInfo);
        return bookInfo;
    }

    /**
     * 查询全部图书（便于观察多次调用下的耗时统计）
     * 访问：http://127.0.0.1:8080/book/queryAllBook
     */
    @RequestMapping("/queryAllBook")
    public Result queryAllBook() {
        Collection<BookInfo> books = bookMap.values();
        log.info("查询全部图书，共 {} 本", books.size());
        return Result.success(books);
    }

    /**
     * 修改图书
     * 访问：http://127.0.0.1:8080/book/updateBook?bookId=1&bookName=SpringAOP
     */
    @RequestMapping("/updateBook")
    public Result updateBook(BookInfo bookInfo) {
        if (bookInfo.getBookId() == null || !bookMap.containsKey(bookInfo.getBookId())) {
            return Result.error("图书不存在");
        }
        BookInfo old = bookMap.get(bookInfo.getBookId());
        if (bookInfo.getBookName() != null) {
            old.setBookName(bookInfo.getBookName());
        }
        if (bookInfo.getAuthor() != null) {
            old.setAuthor(bookInfo.getAuthor());
        }
        if (bookInfo.getPrice() != null) {
            old.setPrice(bookInfo.getPrice());
        }
        if (bookInfo.getStatus() != null) {
            old.setStatus(bookInfo.getStatus());
        }
        log.info("修改图书成功：{}", old);
        return Result.success(old);
    }
}
