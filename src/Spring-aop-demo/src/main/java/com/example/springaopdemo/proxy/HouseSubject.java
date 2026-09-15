package com.example.springaopdemo.proxy;

/**
 * 代理模式 —— Subject：业务接口类
 * <p>
 * 代理模式主要角色：
 * 1. Subject    ：业务接口类，可以是抽象类或者接口（不一定有）
 * 2. RealSubject：业务实现类，具体的业务执行，也就是被代理对象
 * 3. Proxy      ：代理类，RealSubject 的代理
 * <p>
 * 以房屋租赁为例：
 * Subject = 提前定义了房东做的事情（也是中介要做的事情）
 * RealSubject = 房东
 * Proxy = 中介
 */
public interface HouseSubject {

    /**
     * 出租房子
     */
    void rentHouse();

    /**
     * 出售房子（后续新增的业务：中介又新增了代理房屋出售）
     */
    void saleHouse();
}
