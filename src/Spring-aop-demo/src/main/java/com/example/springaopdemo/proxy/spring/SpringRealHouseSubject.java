package com.example.springaopdemo.proxy.spring;

import com.example.springaopdemo.proxy.HouseSubject;
import org.springframework.stereotype.Component;

/**
 * 交给 Spring 管理的"房东"
 * <p>
 * 要用 context.getBean() 观察代理，必须先让这些类被 Spring 管理（加 @Component），
 * 并且有切面匹配它们，Spring 才会为其生成代理对象。
 * <p>
 * 这里单独放在 proxy.spring 包，避免和 proxy 包那些"纯 new 出来演示"的类混在一起。
 */
@Component
public class SpringRealHouseSubject implements HouseSubject {

    @Override
    public void rentHouse() {
        System.out.println("我是房东(Spring Bean), 我出租房子");
    }

    @Override
    public void saleHouse() {
        System.out.println("我是房东(Spring Bean), 我出售房子");
    }
}
