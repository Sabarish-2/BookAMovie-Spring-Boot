package com.bam.movie_and_theatre_module.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerDocumentation {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Movie REST API Documentation")
                        .description("This is the REST API Documentation for the Movie and Theatre Module")
                        .version("v1.0"));
    }
}