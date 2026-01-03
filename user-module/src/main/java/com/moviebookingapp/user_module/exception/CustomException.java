package com.moviebookingapp.user_module.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class CustomException extends RuntimeException {

    private final long serialVersionUIDPerException;

    private final HttpStatus status;

    private final String message;

    protected CustomException(long serialVersionUIDPerException, HttpStatus status, String message) {
        this.serialVersionUIDPerException = serialVersionUIDPerException;
        this.status = status;
        this.message = message;
    }
}
