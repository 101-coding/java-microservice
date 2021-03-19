package com.coding.sk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SkApplicationTests {

	@Test
	void contextLoads() {
		SkApplication sk = new SkApplication();
		Assertions.assertEquals(sk.hello("sowmithra"), "Hello sowmithra!");
	}
}
