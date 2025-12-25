package com.bam.movie_and_theatre_module.global;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    // 1. Handle Specific Business Exceptions (e.g., Resource Not Found)
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorResponseJSON> handleNotFound(ResourceNotFoundException ex, WebRequest request) {
//        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
//    }

    // 2. Handle Validation Errors (e.g., @Valid failure)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseRecord> handleValidation(MethodArgumentNotValidException ex, WebRequest request) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return buildResponse(HttpStatus.NOT_ACCEPTABLE, errors, request);
    }

//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<ErrorResponseJSON> handleCustomExceptions(CustomException ex, WebRequest request) {
//        return buildResponse(ex.getStatus(), ex.getMessage(), request);
//    }

    // 3. Catch-all for any other RuntimeExceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseRecord> handleGlobal(Exception ex, WebRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request);
    }

    private ResponseEntity<ErrorResponseRecord> buildResponse(HttpStatus status, String message, WebRequest request) {
        ErrorResponseRecord error = new ErrorResponseRecord(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getDescription(false).replace("uri=", "")
        );
        return new ResponseEntity<>(error, status);
    }
}

