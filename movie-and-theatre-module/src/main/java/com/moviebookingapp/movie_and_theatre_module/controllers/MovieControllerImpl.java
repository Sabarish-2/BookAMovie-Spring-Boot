package com.moviebookingapp.movie_and_theatre_module.controllers;

import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.dtos.UpdateMovieDTO;
import com.moviebookingapp.movie_and_theatre_module.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller implementation for managing movie-related operations.
 * This class handles HTTP requests for creating, retrieving, and managing
 * movies.
 */
@RestController
// @RequestMapping("/api/v1.0/moviebooking/movies")
public class MovieControllerImpl implements MovieController {

    /**
     * Service for handling movie-related business logic.
     */
    private MovieService movieService;

    /**
     * Sets the movie service.
     *
     * @param movieService The movie service to set.
     */
    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Retrieves all movies.
     *
     * @return A response entity containing a list of all movies.
     */
    @Override
    @GetMapping("/all")
    public ResponseEntity<List<MovieDTO>> viewAllMovies() {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    /**
     * Retrieves a movie by its name and theatre.
     *
     * @param movieName   The name of the movie.
     * @param theatreName The name of the theatre.
     * @return A response entity containing the movie details.
     */
    @Override
    @GetMapping("/{movieName}/{theatreName}")
    public ResponseEntity<MovieDTO> getMovieByNameAndTheatre(String movieName, String theatreName) {
        return new ResponseEntity<>(movieService.getMovieByID(movieName, theatreName), HttpStatus.OK);
    }

    /**
     * Creates a new movie.
     *
     * @param movieDTO The movie details to create.
     * @return A response entity containing the created movie.
     */
    @Override
    @PostMapping("create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieDTO> createMovie(MovieDTO movieDTO) {
        return new ResponseEntity<>(movieService.addMovie(movieDTO), HttpStatus.CREATED);
    }

    /**
     * Updates an existing movie.
     *
     * @param movieName      The name of the movie to update.
     * @param theatreName    The name of the theatre where the movie is shown.
     * @param updateMovieDTO The updated movie details.
     * @return A response entity containing the updated movie.
     */
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{movieName}/update/{theatreName}")
    public ResponseEntity<MovieDTO> updateMovie(String movieName, String theatreName, UpdateMovieDTO updateMovieDTO) {
        return ResponseEntity.ok(movieService.updateMovie(movieName, theatreName, updateMovieDTO));
    }

    /**
     * Searches for movies by name and theatre.
     *
     * @param movieName   The name of the movie to search for.
     * @param theatreName The name of the theatre to search in.
     * @return A response entity containing a list of matching movies.
     */
    @Override
    @GetMapping("search")
    public ResponseEntity<List<MovieDTO>> searchMovies(String movieName, String theatreName) {
        return new ResponseEntity<>(movieService.searchMovies(movieName, theatreName), HttpStatus.OK);
    }

    /**
     * Deletes a movie by its name and theatre.
     *
     * @param movieName   The name of the movie to delete.
     * @param theatreName The name of the theatre where the movie is shown.
     * @return A response entity with a success message.
     */
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{movieName}/delete/{theatreName}")
    public ResponseEntity<String> deleteMovie(String movieName, String theatreName) {
        movieService.deleteMovie(movieName, theatreName);
        return ResponseEntity.ok("Movie " + movieName + " At " + theatreName + " Deleted Successfully!");
    }
}