package com.moviebookingapp.movie_and_theatre_module.services;

import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.dtos.UpdateMovieDTO;

import java.util.List;

/**
 * Service interface for managing movies.
 * This interface defines the contract for movie-related operations.
 */
public interface MovieService {

    /**
     * Adds a new movie to the system.
     *
     * @param movieDTO Data transfer object containing movie details.
     * @return The added movie as a DTO.
     */
    MovieDTO addMovie(MovieDTO movieDTO);

    /**
     * Retrieves all movies in the system.
     *
     * @return A list of all movies as DTOs.
     */
    List<MovieDTO> getAllMovies();

    /**
     * Searches for movies based on movie name and theatre name.
     *
     * @param movieName   The name of the movie to search for.
     * @param theatreName The name of the theatre to search in.
     * @return A list of matching movies as DTOs.
     */
    List<MovieDTO> searchMovies(String movieName, String theatreName);

    /**
     * Retrieves a movie by its ID.
     *
     * @param movieName   The name of the movie.
     * @param theatreName The name of the theatre.
     * @return The movie as a DTO.
     */
    MovieDTO getMovieByID(String movieName, String theatreName);

    /**
     * Updates the details of an existing movie.
     *
     * @param movieName      The name of the movie to update.
     * @param theatreName    The name of the theatre where the movie is located.
     * @param updateMovieDTO Data transfer object containing updated movie details.
     * @return The updated movie as a DTO.
     */
    MovieDTO updateMovie(String movieName, String theatreName, UpdateMovieDTO updateMovieDTO);

    /**
     * Deletes a movie from the system.
     *
     * @param movieName   The name of the movie to delete.
     * @param theatreName The name of the theatre where the movie is located.
     */
    void deleteMovie(String movieName, String theatreName);
}
