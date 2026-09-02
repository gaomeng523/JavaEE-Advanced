package com.spring.ioc;

import com.spring.ioc.Configuration.UserConfiguration;
import com.spring.ioc.Repository.UserRepository;
import com.spring.ioc.Service.UserService;
import com.spring.ioc.controller.UserController;
import org.apache.catalina.User;
import org.springframework.context.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringIocDemoApplication {
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringIocDemoApplication.class, args);

		//测试从IOC容器获取Bean示例
//		UserController userController = (UserController) context.getBean("userController");
//		userController.sayHi();
//		UserController userController1 = context.getBean(UserController.class);
//		UserController userController2 = context.getBean("userController" , UserController.class);
//		System.out.println(userController);
//		System.out.println(userController1);
//		System.out.println(userController2);
//		UserService userService = context.getBean(UserService.class);
//		userService.sayHi("zhangsan");

//		UserRepository userRepository = context.getBean(UserRepository.class);
//		userRepository.sayHi();

		UserConfiguration userConfiguration = context.getBean(UserConfiguration.class);
		userConfiguration.sayHi();
	}
}
