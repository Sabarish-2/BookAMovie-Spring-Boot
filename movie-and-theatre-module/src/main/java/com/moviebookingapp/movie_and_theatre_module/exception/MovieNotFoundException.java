package com.moviebookingapp.movie_and_theatre_module.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;

public class MovieNotFoundException extends CustomException {

    @Serial
    private static final long serialVersionUID = 4L;

    public MovieNotFoundException() {
        super(serialVersionUID, HttpStatus.NOT_FOUND, "No Movies Found!");
    }
    public MovieNotFoundException(String movieName, String theatreName) {
        super(serialVersionUID, HttpStatus.NOT_FOUND, "Movie: " + movieName + " at " + theatreName + " Does Not Exists!");
    }
}
