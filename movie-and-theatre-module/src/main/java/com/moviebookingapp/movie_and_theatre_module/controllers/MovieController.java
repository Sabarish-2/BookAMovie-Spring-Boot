package com.moviebookingapp.movie_and_theatre_module.controllers;

import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.dtos.UpdateMovieDTO;
import com.moviebookingapp.movie_and_theatre_module.exceptions.MovieAlreadyExistsException;
import com.moviebookingapp.movie_and_theatre_module.exceptions.MovieNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface MovieController {
    ResponseEntity<List<MovieDTO>> viewAllMovies() throws MovieNotFoundException;
    ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody MovieDTO movieDTO) throws MovieAlreadyExistsException;
    ResponseEntity<MovieDTO> updateMovie(@NotBlank(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.movieName.invalid}") @PathVariable String movieName, @NotBlank(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.theatreName.invalid}") @PathVariable String theatreName, @Valid @RequestBody UpdateMovieDTO updateMovieDTO) throws MovieNotFoundException;
    ResponseEntity<List<MovieDTO>> searchMovies(@RequestParam(required = false) String movieName, @RequestParam(required = false) String theatreName) throws MovieNotFoundException;
    void deleteMovie(@NotBlank(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.movieName.invalid}") @PathVariable String movieName, @NotBlank(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.theatreName.invalid}") @PathVariable String theatreName) throws MovieNotFoundException;
}
