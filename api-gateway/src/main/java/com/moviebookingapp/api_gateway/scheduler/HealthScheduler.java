package com.moviebookingapp.api_gateway.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
public class HealthScheduler {

    public static volatile long lastActivity = System.currentTimeMillis();

    private final RestTemplate restTemplate = new RestTemplate();

    private final List<String> services = List.of(
        "https://bookamovie-spring-boot-eureka-server-fe3s.onrender.com",
        "https://bookamovie-spring-boot-user-service.onrender.com",
        "https://bookamovie-spring-boot-tickets-service.onrender.com",
        "https://bookamovie-spring-boot-movie-service-5c8d.onrender.com",
"https://bookamovie-spring-boot-api-gateway-2psv.onrender.com/render"
    );

    @Scheduled(initialDelay = 0, fixedRate = 300000)
    public void wakeCall() {
        long now = System.currentTimeMillis();
        long diff = now - lastActivity;

        System.out.println("Schedulre Start: \nDIFF - - " + diff);
        if (diff < 1800000) {
            for (String url: services) {
                CompletableFuture.runAsync(() -> {
                    try {
                        restTemplate.getForObject(url, String.class);
                        System.out.println("Sucess? for url - " + url);
                    } catch (Exception ex) {
                        System.out.println("Error UrL - " + url + " Err is - " +  ex.getMessage());
}
                });
            }
        }
    }
}