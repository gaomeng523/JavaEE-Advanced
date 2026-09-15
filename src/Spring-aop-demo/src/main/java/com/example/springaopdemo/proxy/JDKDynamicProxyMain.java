package com.example.springaopdemo.proxy;

import java.lang.reflect.Proxy;

/**
 * JDK 动态代理 —— 创建代理对象并使用
 * <p>
 * Proxy 类中使用频率最高的方法是 newProxyInstance()，一共 3 个参数：
 * ● loader    ：类加载器，用于加载代理对象
 * ● interfaces：被代理类实现的一些接口
 * （这个参数的定义也决定了 JDK 动态代理只能代理实现了接口的类）
 * ● h         ：实现了 InvocationHandler 接口的对象
 */
public class JDKDynamicProxyMain {

    public static void main(String[] args) {
        HouseSubject target = new RealHouseSubject();
        // 创建一个代理类：通过被代理类、被代理实现的接口、方法调用处理器来创建
        HouseSubject proxy = (HouseSubject) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                new Class[]{HouseSubject.class},
                new JDKInvocationHandler(target)
        );
        proxy.rentHouse();
        System.out.println("-------------------------");
        proxy.saleHouse();
    }
}
