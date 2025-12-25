package com.bam.movie_and_theatre_module;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MovieAndTheatreModuleApplicationTests {

//	@Autowired
//	private MovieController movieController;

	@Test
	void contextLoads() {
//		assertNotNull(movieController);
	}
	@Test
	void test_main() {
		MovieAndTheatreModuleApplication.main(new String[] {});
		assertTrue(true);
	}

}
