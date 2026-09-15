package com.example.springaopdemo.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK 动态代理 —— 自定义 InvocationHandler，并重写 invoke 方法
 * <p>
 * JDK 动态代理实现步骤：
 * 1. 定义一个接口及其实现类（HouseSubject 和 RealHouseSubject）
 * 2. 自定义 InvocationHandler 并重写 invoke 方法，在 invoke 方法中调用目标方法并自定义处理逻辑
 * 3. 通过 Proxy.newProxyInstance(ClassLoader, Class&lt;?&gt;[], InvocationHandler) 创建代理对象
 * <p>
 * InvocationHandler 是 Java 动态代理的关键接口之一，它定义了单一方法 invoke()，用于处理被代理对象的方法调用：
 * proxy  ：代理对象
 * method ：代理对象需要实现的方法，即其中需要重写的方法
 * args   ：method 所对应方法的参数
 */
public class JDKInvocationHandler implements InvocationHandler {

    /**
     * 目标对象，即被代理对象
     */
    private final Object target;

    public JDKInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 代理增强内容
        System.out.println("我是中介, 开始代理");
        // 通过反射调用被代理类的方法
        Object retVal = method.invoke(target, args);
        // 代理增强内容
        System.out.println("我是中介, 代理结束");
        return retVal;
    }
}
