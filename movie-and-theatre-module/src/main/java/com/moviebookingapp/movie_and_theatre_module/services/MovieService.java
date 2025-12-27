package com.moviebookingapp.movie_and_theatre_module.services;

import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.dtos.UpdateMovieDTO;

import java.util.List;

public interface MovieService {
    MovieDTO addMovie(MovieDTO movieDTO);
    List<MovieDTO> getAllMovies();
    List<MovieDTO> searchMovies(String movieName, String theatreName);
    MovieDTO getMovieByID(String movieName, String theatreName);
    MovieDTO updateMovie(String movieName, String theatreName, UpdateMovieDTO updateMovieDTO);
//    MovieDTO setStatus(String movieName, String theatreName, MovieStatus movieStatus);
    void deleteMovie(String movieName, String theatreName);
}
