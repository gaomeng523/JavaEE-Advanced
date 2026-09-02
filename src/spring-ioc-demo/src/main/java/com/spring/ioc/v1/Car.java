package com.spring.ioc.v1;

public class Car {
    private Framework framework;

    public Car(int size) {
        framework = new Framework(size);
        System.out.println("Car ini......");
    }
    public void run(){
        System.out.println("Car run......");
    }
}
