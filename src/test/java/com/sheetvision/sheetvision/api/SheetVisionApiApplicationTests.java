package com.sheetvision.sheetvision.api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test") // optional, if you have application-test.properties
@SpringBootTest(classes = SheetVisionApiApplication.class)
class SheetVisionApiApplicationTests {

	@Test
	void contextLoads() {
	}
}