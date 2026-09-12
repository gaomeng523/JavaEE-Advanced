package com.example.book.controller;

import com.example.book.entity.BookInfo;
import com.example.book.entity.PageRequest;
import com.example.book.entity.PageResult;
import com.example.book.entity.Result;
import com.example.book.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("book")
public class BookController {
    @Autowired
    private BookService bookService;
//    @RequestMapping("getList")
//    public Result<List<BookInfo>> getList(){
////        BookService bookService = new BookService();
//        List<BookInfo> bookInfos = bookService.getList();
//        return Result.success(bookInfos);
//    }

    @RequestMapping("addBook")
    public Result<Void> addBook(BookInfo bookInfo){
        if(!StringUtils.hasLength(bookInfo.getBookName()) ||
            !StringUtils.hasLength(bookInfo.getAuthor()) ||
            bookInfo.getCount() == null ||
            bookInfo.getPrice() == null ||
            !StringUtils.hasLength(bookInfo.getPublish()) ||
            bookInfo.getStatus() == null){
                // 参数问题统一用 400，前端直接弹 msg
                return Result.fail(400, "参数不合法");
        }

        Integer result = bookService.addBook(bookInfo);
        // result 表示插入了一条数据，影响行数为一行
        if (result != null && result == 1) {
            // 成功但没有数据要返回，用无参的 success()
            return Result.success();
        }
        return Result.fail("插入图书失败");
    }

    @RequestMapping("getListByPage")
    public Result<PageResult<BookInfo>> getListByPage(PageRequest pageRequest){
        log.info("获取翻页信息 ， pageRequest:{}",pageRequest);

        // 登录校验已经不在这里做了，统一交给 LoginInterceptor 拦截，见 config/WebConfig
        PageResult<BookInfo> pageResult = bookService.getListByPage(pageRequest);
        return Result.success(pageResult);
    }

    @RequestMapping("queryBookById")
    public Result<BookInfo> queryBookById(Integer bookId){
        log.info("查询图书详情,bookId:{}",bookId);

        if(bookId == null || bookId <= 0){
            return Result.fail(400, "参数不正确");
        }
        BookInfo bookInfo = bookService.queryBookById(bookId);
        if(bookInfo == null){
            return Result.fail("图书不存在");
        }
        return Result.success(bookInfo);
    }

    @RequestMapping("updateBook")
    public Result<Void> updateBook(BookInfo bookInfo){
        log.info("修改图书信息,bookInfo:{}",bookInfo);

        if(bookInfo.getId() == null || bookInfo.getId() <= 0){
            return Result.fail(400, "参数不正确");
        }
        Integer result = bookService.updateBook(bookInfo);
        if(result == null){
            return Result.fail("数据更新失败");
        }
        // 影响行数为 0 说明这个 id 在库里不存在
        if(result == 0){
            return Result.fail("图书不存在");
        }
        return Result.success();
    }

    @RequestMapping("batchDeleteBook")
    public Result<Void> batchDeleteBook(Integer[] ids){
        log.info("批量删除图书,ids:{}", Arrays.toString(ids));

        if(ids == null || ids.length == 0){
            return Result.fail(400, "请选择要删除的图书");
        }
        Integer result = bookService.batchDeleteBook(ids);
        if(result == null || result == 0){
            return Result.fail("批量删除失败");
        }
        return Result.success();
    }
}
