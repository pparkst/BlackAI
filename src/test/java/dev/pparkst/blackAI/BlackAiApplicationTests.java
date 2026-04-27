package dev.pparkst.blackAI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class BlackAiApplicationTests {

	@Autowired
	private ApplicationContext ac;

	@Test
	void contextLoads() {
		for (String name : ac.getBeanDefinitionNames()) {
			System.out.println("Bean name : " + name);
		}
	}

}
