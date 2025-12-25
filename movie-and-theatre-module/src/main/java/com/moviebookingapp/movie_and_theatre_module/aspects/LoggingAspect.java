package com.moviebookingapp.movie_and_theatre_module.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution (* com.moviebookingapp.*.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        log.info("Executing Method: {} . {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
    }
    @AfterReturning("execution (* com.moviebookingapp.*.*.*(..))")
    public void afterReturningMethod(JoinPoint joinPoint, Object result) {
        log.info("Method Execution Successful: {} . {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        if (result != null && result.toString().length() < 1000) {
            log.info("Method Result: {}", result);
        }
    }
    @AfterThrowing("execution (* com.moviebookingapp.*.*.*(..))")
    public void afterThrowingMethod(JoinPoint joinPoint, Error error) {
        log.error("Error in Method: {} . {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.error(error.getMessage());
    }

}
