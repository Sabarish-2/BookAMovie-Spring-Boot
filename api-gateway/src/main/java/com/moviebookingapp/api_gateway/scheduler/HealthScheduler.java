package com.moviebookingapp.api_gateway.scheduler;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@EnableScheduling
public class HealthScheduler {

    public static volatile long lastActivity = System.currentTimeMillis();

    private final List<String> services = List.of(
        "https://bookamovie-spring-boot-eureka-server-fe3s.onrender.com",
        "https://bookamovie-spring-boot-user-service.onrender.com",
        "https://bookamovie-spring-boot-tickets-service.onrender.com",
        "https://bookamovie-spring-boot-movie-service-5c8d.onrender.com"
    );

    @Scheduled(initialDelay = 0, fixedRate = 300000)
    public void wakeCall() {
        long now = System.currentTimeMillis();
        long diff = now - lastActivity;

        if (diff < 1800000) {
            for (String url: services) {
                CompletableFuture.runAsync(() -> {
                    try {
                        new RestTemplate().getForObject(url, String.class);
                    } catch (Exception ignored) {}
                });
            }
        }
    }
}