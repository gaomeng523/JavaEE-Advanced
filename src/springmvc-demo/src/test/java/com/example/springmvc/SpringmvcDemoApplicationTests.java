package com.example.springmvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SpringmvcDemoApplicationTests {

	@Test
	void contextLoads() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		// 对象转JSON
		Person person = new Person();
		person.setName("zhansan");
		person.setId(1);
		person.setPassword("12345");

		String json = objectMapper.writeValueAsString(person);
		System.out.println(json);
		//JSON 转 对象

		String jsonStr = "{\"id\":1,\"name\":\"zhansan\",\"password\":\"12345\"}";
		Person p = objectMapper.readValue(jsonStr,Person.class);
		System.out.println(p.toString());
	}

}
