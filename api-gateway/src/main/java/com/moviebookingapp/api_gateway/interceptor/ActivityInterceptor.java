package com.moviebookingapp.api_gateway.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.moviebookingapp.api_gateway.scheduler.HealthScheduler;

@Component
@EnableScheduling
public class ActivityInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Update on every API call
        HealthScheduler.lastActivity = System.currentTimeMillis();

        return true; // true = continue processing the request
    }

}