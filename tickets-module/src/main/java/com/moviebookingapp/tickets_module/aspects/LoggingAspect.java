package com.moviebookingapp.tickets_module.aspects;

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

    @Before("execution (* com.moviebookingapp.*.controllers.*.*(..))")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        log.info("Executing Method: {}->{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
    }
    @AfterReturning(value = "execution (* com.moviebookingapp.*.controllers.*.*(..))", returning = "result")
    public void afterReturningControllerMethod(JoinPoint joinPoint, Object result) {
        log.info("Method Execution Successful: {}->{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        if (result != null && result.toString().length() < 1000) {
            log.info("Method Result: {}", result);
        }
    }
    @AfterThrowing(value = "execution (* com.moviebookingapp.*.controllers.*.*(..))", throwing = "error")
    public void afterThrowingControllerMethod(JoinPoint joinPoint, Exception error) {
        log.error("Error in Method: {}->{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.error(error.getMessage());
    }
    @Before("execution (* com.moviebookingapp.*.services.*.*(..))")
    public void beforeServicesMethod(JoinPoint joinPoint) {
        log.info("Executing Method: {}->{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
    }
    @AfterReturning(value = "execution (* com.moviebookingapp.*.services.*.*(..))", returning = "result")
    public void afterReturningServicesMethod(JoinPoint joinPoint, Object result) {
        log.info("Method Execution Successful: {}->{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        if (result != null && result.toString().length() < 1000) {
            log.info("Method Result: {}", result);
        }
    }
    @AfterThrowing(value = "execution (* com.moviebookingapp.*.services.*.*(..))", throwing = "error")
    public void afterThrowingServicesMethod(JoinPoint joinPoint, Exception error) {
        log.error("Error in Method: {}->{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.error(error.getMessage());
    }


    @Before("execution (* com.moviebookingapp.*.repositories.*.*(..))")
    public void beforeRepositoriesMethod(JoinPoint joinPoint) {
        log.info("Executing Method: {}->{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
    }
    @AfterReturning(value = "execution (* com.moviebookingapp.*.repositories.*.*(..))", returning = "result")
    public void afterReturningRepositoriesMethod(JoinPoint joinPoint, Object result) {
        log.info("Method Execution Successful: {}->{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        if (result != null && result.toString().length() < 1000) {
            log.info("Method Result: {}", result);
        }
    }
    @AfterThrowing(value = "execution (* com.moviebookingapp.*.repositories.*.*(..))", throwing = "error")
    public void afterThrowingRepositoriesMethod(JoinPoint joinPoint, Exception error) {
        log.error("Error in Method: {}->{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        log.error(error.getMessage());
    }

}
