package com.spring.ioc.v1;

public class Framework {
    private Bottom bottom;

    public Framework(int size) {
        bottom = new Bottom(size);
        System.out.println("Framework init......");
    }
}
