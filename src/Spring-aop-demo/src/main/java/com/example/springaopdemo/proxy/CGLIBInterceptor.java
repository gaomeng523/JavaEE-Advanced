package com.example.springaopdemo.proxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLIB 动态代理 —— 自定义 MethodInterceptor（方法拦截器）
 * <p>
 * JDK 动态代理有一个最致命的问题：只能代理实现了接口的类。
 * 有些场景下业务代码是直接实现的、没有接口定义，这时可以用 CGLIB 动态代理。
 * <p>
 * CGLIB（Code Generation Library）是一个基于 ASM 的字节码生成库，允许在运行时对字节码进行修改和动态生成。
 * CGLIB 通过继承方式实现代理，很多知名开源框架都用到它。例如 Spring 的 AOP 模块：
 * 如果目标对象实现了接口，默认采用 JDK 动态代理，否则采用 CGLIB 动态代理。
 * <p>
 * CGLIB 动态代理实现步骤：
 * 1. 定义一个类（被代理类）
 * 2. 自定义 MethodInterceptor 并重写 intercept 方法，intercept 用于增强目标方法，和 JDK 的 invoke 类似
 * 3. 通过 Enhancer 类的 create() 创建代理类
 * <p>
 * MethodInterceptor 的参数说明：
 * o           ：被代理的对象
 * method      ：目标方法（被拦截的方法，也就是需要增强的方法）
 * objects     ：方法入参
 * methodProxy ：用于调用原始方法
 * <p>
 * 小提示：CGLIB 本身是一个独立开源项目，但这里 import 的是 org.springframework.cglib.proxy.*，
 * 属于 Spring 自己 repackage 过的 CGLIB，已经包含在 spring-core 中，所以不需要额外加依赖。
 */
public class CGLIBInterceptor implements MethodInterceptor {

    /**
     * 目标对象，即被代理对象
     */
    private final Object target;

    public CGLIBInterceptor(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        // 代理增强内容
        System.out.println("我是中介, 开始代理");
        // 通过反射调用被代理类的方法
        Object retVal = methodProxy.invoke(target, objects);
        // 代理增强内容
        System.out.println("我是中介, 代理结束");
        return retVal;
    }
}
