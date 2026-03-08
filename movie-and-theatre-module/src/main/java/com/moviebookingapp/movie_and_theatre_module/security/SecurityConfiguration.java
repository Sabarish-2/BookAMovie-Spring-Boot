package com.moviebookingapp.movie_and_theatre_module.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for application security.
 * This class defines the security filter chain and handles authentication and
 * authorization.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    /**
     * Configures the security filter chain for the application.
     *
     * @param httpSecurity The HttpSecurity object to configure.
     * @param jwtFilter    The JWT filter for handling authentication.
     * @return The configured SecurityFilterChain.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, JWTFilter jwtFilter) {

        httpSecurity.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**", "/actuator/health").permitAll()
                .requestMatchers(HttpMethod.GET, "/all", "/search",).permitAll()
                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.exceptionHandling(ex -> ex.authenticationEntryPoint((request, response, authException) -> {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write(
                    "{\"message\": \"Authentication Required\", \"reason\": \"" + authException.getMessage() + "\"}");
        }).accessDeniedHandler((request, response, accessDeniedException) -> {
            response.setStatus(403);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"You Are Not Supposed To Be Accessing This!!\", \"reason\": \""
                    + accessDeniedException.getMessage() + "\"}");
        }));

        return httpSecurity.build();
    }
}
