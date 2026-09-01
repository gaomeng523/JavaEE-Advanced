package com.example.book.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookInfo {
    private Integer id;
    private String bookName;
    private String author;
    private Integer count;
    private BigDecimal price;
    private String publish;
    private Integer status;
    private String statusCN;
}
