package com.moviebookingapp.movie_and_theatre_module.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class IncorrectTicketsAllottedException extends CustomException {

    @Serial
    private static final long serialVersionUID = 3L;

    public IncorrectTicketsAllottedException(String message) {
        super(serialVersionUID, HttpStatus.NOT_ACCEPTABLE, message);
    }
}
