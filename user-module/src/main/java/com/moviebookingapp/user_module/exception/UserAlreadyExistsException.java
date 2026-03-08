package com.moviebookingapp.user_module.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class UserAlreadyExistsException extends CustomException {

    @Serial
    private static final long serialVersionUID = 9L;

    public UserAlreadyExistsException(String message) {
        super(serialVersionUID, HttpStatus.CONFLICT, message);
    }
}
