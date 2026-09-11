package com.example.book.entity;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {
    private List<T> records;
    private Integer count;

    private PageRequest request;
    public PageResult (List<T> records, Integer count,PageRequest request) {
        this.records = records;
        this.count = count;
        this.request = request;
    }
}
