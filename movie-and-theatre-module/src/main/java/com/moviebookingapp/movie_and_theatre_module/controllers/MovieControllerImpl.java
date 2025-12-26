package com.moviebookingapp.movie_and_theatre_module.controllers;

import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.dtos.UpdateMovieDTO;
import com.moviebookingapp.movie_and_theatre_module.exceptions.MovieAlreadyExistsException;
import com.moviebookingapp.movie_and_theatre_module.exceptions.MovieNotFoundException;
import com.moviebookingapp.movie_and_theatre_module.services.MovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1.0/moviebooking")
public class MovieControllerImpl implements MovieController {

    private MovieService movieService;

    @Autowired
    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("all")
    public ResponseEntity<List<MovieDTO>> viewAllMovies() throws MovieNotFoundException {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody MovieDTO movieDTO) throws MovieAlreadyExistsException {
        return new ResponseEntity<>(movieService.addMovie(movieDTO), HttpStatus.CREATED);
    }

    @PutMapping("{movieName}/update/{theatreName}")
    public ResponseEntity<MovieDTO> updateMovie(@NotBlank(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.movieName.invalid}") @PathVariable String movieName, @NotBlank(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.theatreName.invalid}") @PathVariable String theatreName, @Valid @RequestBody UpdateMovieDTO updateMovieDTO) throws MovieNotFoundException {
        return ResponseEntity.ok(movieService.updateMovie(movieName, theatreName, updateMovieDTO));
    }

    @GetMapping("movies/search")
    public ResponseEntity<List<MovieDTO>> searchMovies(@RequestParam(required = false) String movieName, @RequestParam(required = false) String theatreName) throws MovieNotFoundException {
        return new ResponseEntity<>(movieService.searchMovies(movieName, theatreName), HttpStatus.OK);
    }

    @DeleteMapping("{movieName}/delete/{theatreName}")
    public void deleteMovie(@NotBlank(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.movieName.invalid}") @PathVariable String movieName, @NotBlank(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.theatreName.invalid}") @PathVariable String theatreName) throws MovieNotFoundException {
        movieService.deleteMovie(movieName, theatreName);
    }
}