package com.moviebookingapp.movie_and_theatre_module.exceptions;

import java.io.Serial;

public class MovieAlreadyExistsException extends Exception {

    @Serial
    private static final long serialVersionUID = 5L;

    public MovieAlreadyExistsException(String message) {
        super(message);
    }
}
