package com.spring.ioc.Repository;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    public void sayHi() {
        System.out.println("Hi, UserRepository~");
    }
}
