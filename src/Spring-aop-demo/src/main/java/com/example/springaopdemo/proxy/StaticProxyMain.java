package com.example.springaopdemo.proxy;

/**
 * 静态代理 —— 使用示例
 * <p>
 * 运行结果：
 * 我是中介, 开始代理
 * 我是房东, 我出租房子
 * 我是中介, 代理结束
 * 我是中介, 开始代理
 * 我是房东, 我出售房子
 * 我是中介, 代理结束
 * <p>
 * 说明：日常开发几乎看不到静态代理的场景。
 */
public class StaticProxyMain {

    public static void main(String[] args) {
        // 目标对象（被代理对象）
        HouseSubject subject = new RealHouseSubject();
        // 创建代理类
        HouseProxy proxy = new HouseProxy(subject);
        // 通过代理类访问目标方法
        proxy.rentHouse();
        System.out.println("-------------------------");
        proxy.saleHouse();
    }
}
