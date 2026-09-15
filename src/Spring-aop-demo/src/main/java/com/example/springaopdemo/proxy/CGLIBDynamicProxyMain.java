package com.example.springaopdemo.proxy;

import org.springframework.cglib.proxy.Enhancer;

/**
 * CGLIB 动态代理 —— 创建代理类并使用
 * <p>
 * Enhancer.create(Class type, Callback callback) 用来生成一个代理对象：
 * ● type    ：被代理类的类型（类或接口）
 * ● callback：自定义方法拦截器 MethodInterceptor
 */
public class CGLIBDynamicProxyMain {

    public static void main(String[] args) {
        RealHouseSubject target = new RealHouseSubject();
        // CGLIB 通过"继承"被代理类来生成代理对象
        RealHouseSubject proxy = (RealHouseSubject) Enhancer.create(
                target.getClass(), new CGLIBInterceptor(target));
        proxy.rentHouse();
        System.out.println("-------------------------");
        proxy.saleHouse();
        // 打印一下代理对象的真实类型，可以看到是 RealHouseSubject 的子类
        System.out.println("代理对象类型：" + proxy.getClass().getName());
    }
}
