package com.moviebookingapp.movie_and_theatre_module.services;

import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.enums.MovieStatus;
import com.moviebookingapp.movie_and_theatre_module.exceptions.MovieAlreadyExistsException;
import com.moviebookingapp.movie_and_theatre_module.exceptions.MovieNotFoundException;

import java.util.List;

public interface MovieService {
    MovieDTO addMovie(MovieDTO movieDTO) throws MovieAlreadyExistsException;
    List<MovieDTO> getAllMovies() throws MovieNotFoundException;
    MovieDTO setStatus(String movieName, String theatreName, MovieStatus movieStatus) throws MovieNotFoundException;
    void deleteMovie(String movieName, String theatreName) throws MovieNotFoundException;
}
