package com.spring.ioc.v2;

public class Car {
    private Framework framework;
    public Car(Framework framework) {
        this.framework = framework;
        System.out.println("Car init....");
    }
    public void run() {
        System.out.println("Car run...");
    }
}
