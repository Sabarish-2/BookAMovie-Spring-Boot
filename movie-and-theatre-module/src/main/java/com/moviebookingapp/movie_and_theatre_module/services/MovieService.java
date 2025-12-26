package com.moviebookingapp.movie_and_theatre_module.services;

import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.dtos.UpdateMovieDTO;
import com.moviebookingapp.movie_and_theatre_module.exceptions.MovieAlreadyExistsException;
import com.moviebookingapp.movie_and_theatre_module.exceptions.MovieNotFoundException;

import java.util.List;

public interface MovieService {
    MovieDTO addMovie(MovieDTO movieDTO) throws MovieAlreadyExistsException;
    List<MovieDTO> getAllMovies() throws MovieNotFoundException;
    List<MovieDTO> searchMovies(String movieName, String theatreName) throws MovieNotFoundException;
    MovieDTO getMovieByID(String movieName, String theatreName) throws MovieNotFoundException;
    MovieDTO updateMovie(String movieName, String theatreName, UpdateMovieDTO updateMovieDTO) throws MovieNotFoundException;
//    MovieDTO setStatus(String movieName, String theatreName, MovieStatus movieStatus) throws MovieNotFoundException;
    void deleteMovie(String movieName, String theatreName) throws MovieNotFoundException;
}
