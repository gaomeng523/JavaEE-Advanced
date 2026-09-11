package com.example.book.entity;

import lombok.Data;

@Data
public class PageRequest {
    private int currentPage = 1;
    private int pageSize = 10;

    // 参数兜底：防止前端传 currentPage=0 导致 offset 为负数，或 pageSize 过大把整表拉走
    public int getCurrentPage(){
        return currentPage < 1 ? 1 : currentPage;
    }

    public int getPageSize(){
        return (pageSize < 1 || pageSize > 100) ? 10 : pageSize;
    }

    public int getOffset(){
        return (getCurrentPage() - 1) * getPageSize();
    }
}
