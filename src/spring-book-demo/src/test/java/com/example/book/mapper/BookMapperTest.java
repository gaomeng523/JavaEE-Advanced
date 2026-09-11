package com.example.book.mapper;

import com.example.book.entity.PageRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;
    @Test
    void getListByLimit() {
        PageRequest pageRequest = new PageRequest();
        System.out.println(bookMapper.getListByLimit(pageRequest));
    }

    @Test
    void count() {
        System.out.println(bookMapper.count());
    }
}