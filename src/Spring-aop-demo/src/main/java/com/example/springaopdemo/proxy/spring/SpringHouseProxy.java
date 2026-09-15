package com.example.springaopdemo.proxy.spring;

import com.example.springaopdemo.proxy.HouseSubject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 交给 Spring 管理的"中介"
 * <p>
 * 相当于静态代理里的 HouseProxy，只不过加了 @Component 成为 Spring Bean，
 * 好让 Spring AOP 有机会给它生成代理对象。
 * <p>
 * 注意构造器注入：HouseSubject 有两个实现类（SpringRealHouseSubject、SpringHouseProxy），
 * 只按类型注入会报 NoUniqueBeanDefinitionException，所以这里用 @Qualifier 指定。
 */
@Component
public class SpringHouseProxy implements HouseSubject {

    private final HouseSubject houseSubject;

    public SpringHouseProxy(@Qualifier("springRealHouseSubject") HouseSubject houseSubject) {
        this.houseSubject = houseSubject;
    }

    @Override
    public void rentHouse() {
        System.out.println("我是中介(Spring Bean), 开始代理");
        houseSubject.rentHouse();
        System.out.println("我是中介(Spring Bean), 代理结束");
    }

    @Override
    public void saleHouse() {
        System.out.println("我是中介(Spring Bean), 开始代理");
        houseSubject.saleHouse();
        System.out.println("我是中介(Spring Bean), 代理结束");
    }
}
