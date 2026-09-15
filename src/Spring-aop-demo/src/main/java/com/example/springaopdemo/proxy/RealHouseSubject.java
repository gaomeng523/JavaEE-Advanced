package com.example.springaopdemo.proxy;

/**
 * 静态代理 —— RealSubject：房东（被代理对象）
 */
public class RealHouseSubject implements HouseSubject {

    @Override
    public void rentHouse() {
        System.out.println("我是房东, 我出租房子");
    }

    @Override
    public void saleHouse() {
        System.out.println("我是房东, 我出售房子");
    }
}
