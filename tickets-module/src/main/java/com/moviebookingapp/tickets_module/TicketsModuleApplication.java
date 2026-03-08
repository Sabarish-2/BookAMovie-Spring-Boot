package com.moviebookingapp.tickets_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TicketsModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketsModuleApplication.class, args);
	}

}
