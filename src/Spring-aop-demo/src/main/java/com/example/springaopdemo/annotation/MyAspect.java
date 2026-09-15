package com.example.springaopdemo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，配合 @annotation 切点表达式使用
 * <p>
 * 1. @Target：标识注解可以修饰的对象范围
 * ElementType.TYPE      —— 类、接口（含注解类型）或枚举声明
 * ElementType.METHOD    —— 方法
 * ElementType.PARAMETER —— 参数
 * ElementType.TYPE_USE  —— 任意类型
 * <p>
 * 2. @Retention：标识注解的生命周期
 * RetentionPolicy.SOURCE  —— 仅存在于源码，编译成字节码后被丢弃（如 @Data、@Slf4j）
 * RetentionPolicy.CLASS   —— 存在于源码和字节码，运行时丢弃
 * RetentionPolicy.RUNTIME —— 源码、字节码、运行时都存在，可通过反射获取（如 @Controller、@ResponseBody）
 * <p>
 * 要让 AOP 在运行时读到这个注解，必须用 RUNTIME。
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAspect {
}
