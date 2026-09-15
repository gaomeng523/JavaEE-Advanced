package com.example.springaopdemo.model;

import lombok.Data;

/**
 * 图书实体（BookController 的入参 / 返回值）
 */
@Data
public class BookInfo {

    /** 图书 ID */
    private Integer bookId;

    /** 图书名称 */
    private String bookName;

    /** 作者 */
    private String author;

    /** 价格 */
    private Double price;

    /** 状态：0-不可借阅 1-可借阅 */
    private Integer status;

    public BookInfo() {
    }

    public BookInfo(Integer bookId, String bookName, String author, Double price, Integer status) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.price = price;
        this.status = status;
    }
}
