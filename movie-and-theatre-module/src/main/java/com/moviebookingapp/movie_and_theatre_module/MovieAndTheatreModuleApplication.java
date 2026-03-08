package com.moviebookingapp.movie_and_theatre_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class MovieAndTheatreModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieAndTheatreModuleApplication.class, args);
	}

}
