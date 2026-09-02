package com.spring.ioc.Configuration;

import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfiguration {
    public void sayHi() {
        System.out.println("Hi,UserConfiguration~");
    }
}
