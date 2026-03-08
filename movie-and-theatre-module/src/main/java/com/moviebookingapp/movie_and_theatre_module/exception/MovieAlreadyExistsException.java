package com.moviebookingapp.movie_and_theatre_module.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class MovieAlreadyExistsException extends CustomException {

    @Serial
    private static final long serialVersionUID = 9L;

    public MovieAlreadyExistsException(String message) {
        super(serialVersionUID, HttpStatus.CONFLICT, message);
    }
}
