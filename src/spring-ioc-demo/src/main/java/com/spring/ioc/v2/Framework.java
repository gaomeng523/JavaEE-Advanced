package com.spring.ioc.v2;

public class Framework {
    private Bottom bottom;
    public Framework(Bottom bottom) {
        this.bottom = bottom;
        System.out.println("Framework init...");
    }
}
