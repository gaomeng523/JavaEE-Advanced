package com.spring.ioc.Service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public void sayHi(String name) {
        System.out.println("Hi," + name);
    }
}
