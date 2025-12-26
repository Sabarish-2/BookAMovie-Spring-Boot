package com.moviebookingapp.movie_and_theatre_module.exceptions;

import java.io.Serial;

public class MovieNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 4L;

    public MovieNotFoundException() {
        super("No Movies Found!");
    }
    public MovieNotFoundException(String movieName, String theatreName) {
        super("Movie: " + movieName + " at " + theatreName + " Does Not Exists!");
    }
}
