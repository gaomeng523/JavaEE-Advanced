package com.spring.ioc.v1;

public class Tire {
    private int size;
    public Tire(int size){
        this.size = size;
        System.out.println("轮胎尺寸 : " + size);
    }
}
