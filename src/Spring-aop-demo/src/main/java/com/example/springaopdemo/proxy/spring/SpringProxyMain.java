package com.example.springaopdemo.proxy.spring;

import com.example.springaopdemo.SpringAopDemoApplication;
import com.example.springaopdemo.proxy.HouseSubject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 用 getBean 观察 Spring 生成的代理对象类型
 * <p>
 * proxyTargetClass 与代理方式的对应关系：
 * <pre>
 * proxyTargetClass | 目标对象             | 代理方式
 * false            | 实现了接口            | JDK 代理
 * false            | 未实现接口（只有实现类）| CGLIB 代理
 * true             | 实现了接口            | CGLIB 代理
 * true             | 未实现接口（只有实现类）| CGLIB 代理
 * </pre>
 * <p>
 * 注意：
 * ● Spring Boot 2.x 开始默认使用 CGLIB 代理（proxyTargetClass = true）
 * ● 可以通过配置项 spring.aop.proxy-target-class=false 改为 JDK 代理
 * ● Spring Boot 中设置 @EnableAspectJAutoProxy 无效，因为 Spring Boot 默认使用
 * AopAutoConfiguration 进行装配
 * <p>
 * 验证方式：
 * 1. 默认（CGLIB）运行本类 —— 能正常拿到 SpringHouseProxy 的代理对象；
 * 2. 在 application.yaml 中打开 spring.aop.proxy-target-class=false 再运行 ——
 * getBean(SpringHouseProxy.class) 会抛 BeanNotOfRequiredTypeException，
 * 因为 JDK 代理只能代理接口，此时要用注释里的写法：context.getBean("springHouseProxy")。
 * <p>
 * 为了让程序不中断，这里用 try/catch 把两种结果都打印出来。
 */
@Slf4j
public class SpringProxyMain {

    public static void main(String[] args) {
        // 以非 Web 方式启动，避免和主启动类抢占 8080 端口
        ConfigurableApplicationContext context = new SpringApplicationBuilder(SpringAopDemoApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        System.out.println("=========== 1. 直接按类型 getBean(SpringHouseProxy.class) ===========");
        try {
            // 按具体类型取，只有 CGLIB 代理（proxyTargetClass = true）才拿得到
            SpringHouseProxy houseProxy = context.getBean(SpringHouseProxy.class);
            /*
             * 设置 spring.aop.proxy-target-class=true  —— cglib 代理，运行成功
             * 设置 spring.aop.proxy-target-class=false —— jdk 代理，运行失败，不能代理类
             * 因为 SpringHouseProxy 是一个类，而不是接口，需要修改为：
             * HouseSubject houseProxy = (HouseSubject) context.getBean("springHouseProxy");
             */
            System.out.println("拿到的 Bean 类型：" + houseProxy.getClass().toString());
        } catch (Exception e) {
            System.out.println("按类型获取失败（说明当前是 JDK 动态代理）：" + e.getClass().getSimpleName());
            System.out.println("原因：JDK 动态代理只能代理接口，代理对象是 Proxy 的子类，不是 SpringHouseProxy 类型");
        }

        System.out.println();
        System.out.println("=========== 2. 按接口类型 + Bean 名称获取（JDK / CGLIB 都能成功） ===========");
        HouseSubject houseProxy = (HouseSubject) context.getBean("springHouseProxy");
        System.out.println("拿到的 Bean 类型：" + houseProxy.getClass().toString());
        System.out.println();
        System.out.println("=========== 3. 调用代理对象，观察切面增强 ===========");
        houseProxy.rentHouse();

        context.close();
    }
}
