package com.solera.backendcursojava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendcursojavaApplicationTests {

	@Test
	void contextLoads() {
		// Arrange
		int a = 2;
		int b = 3;

		// Act
		int result = a + b;

		Assertions.assertEquals(5, result);
	}

}
