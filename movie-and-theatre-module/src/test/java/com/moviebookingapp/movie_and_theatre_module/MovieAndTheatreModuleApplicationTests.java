package com.moviebookingapp.movie_and_theatre_module;

import com.moviebookingapp.movie_and_theatre_module.controllers.MovieController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MovieAndTheatreModuleApplicationTests {

	@Autowired
	private MovieController movieController;

	@Test
	void contextLoads() {
		assertNotNull(movieController);
	}
	@Test
	void test_main() {
		MovieAndTheatreModuleApplication.main(new String[] {});
		assertTrue(true);
	}

}
