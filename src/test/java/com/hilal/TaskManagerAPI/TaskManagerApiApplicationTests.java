package com.hilal.TaskManagerAPI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaskManagerApiApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
	void testMainRunsWithoutException() {
		TaskManagerApiApplication.main(new String[]{});
	}

}
