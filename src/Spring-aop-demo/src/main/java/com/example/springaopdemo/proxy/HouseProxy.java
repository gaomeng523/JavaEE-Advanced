package com.example.springaopdemo.proxy;

/**
 * 静态代理 —— Proxy：中介（帮房东出租/出售房子）
 * <p>
 * 静态代理：程序运行前，代理类的 .class 文件就已经存在了。
 * <p>
 * 缺点：代码都写死了，对目标对象的每个方法的增强都是手动完成的，非常不灵活。
 * 修改接口（Subject）和业务实现类（RealSubject）时，还需要修改代理类（Proxy）；
 * 新增一组接口和实现类，也要为每一个业务实现类新增代理类。
 */
public class HouseProxy implements HouseSubject {

    /**
     * 将被代理对象声明为成员变量
     */
    private final HouseSubject houseSubject;

    public HouseProxy(HouseSubject houseSubject) {
        this.houseSubject = houseSubject;
    }

    @Override
    public void rentHouse() {
        // 开始代理
        System.out.println("我是中介, 开始代理");
        // 代理房东出租房子
        houseSubject.rentHouse();
        // 代理结束
        System.out.println("我是中介, 代理结束");
    }

    @Override
    public void saleHouse() {
        // 开始代理
        System.out.println("我是中介, 开始代理");
        // 代理房东出售房子
        houseSubject.saleHouse();
        // 代理结束
        System.out.println("我是中介, 代理结束");
    }
}
