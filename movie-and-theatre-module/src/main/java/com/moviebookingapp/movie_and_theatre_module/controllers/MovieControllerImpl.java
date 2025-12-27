package com.moviebookingapp.movie_and_theatre_module.controllers;

import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.dtos.UpdateMovieDTO;
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

    @Override
    @GetMapping("all")
    public ResponseEntity<List<MovieDTO>> viewAllMovies() {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @Override
    @PostMapping("create")
    public ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody MovieDTO movieDTO) {
        return new ResponseEntity<>(movieService.addMovie(movieDTO), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("{movieName}/update/{theatreName}")
    public ResponseEntity<MovieDTO> updateMovie(String movieName, String theatreName, UpdateMovieDTO updateMovieDTO) {
        return ResponseEntity.ok(movieService.updateMovie(movieName, theatreName, updateMovieDTO));
    }

    @Override
    @GetMapping("movies/search")
    public ResponseEntity<List<MovieDTO>> searchMovies(@RequestParam(required = false) String movieName, @RequestParam(required = false) String theatreName) {
        return new ResponseEntity<>(movieService.searchMovies(movieName, theatreName), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("{movieName}/delete/{theatreName}")
    public ResponseEntity<String> deleteMovie(@NotBlank(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.movieName.invalid}") @PathVariable(required = false) String movieName, @NotBlank(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.theatreName.invalid}") @PathVariable(required = false) String theatreName) {
        movieService.deleteMovie(movieName, theatreName);
        return ResponseEntity.ok("Movie " + movieName + " At " + theatreName + " Deleted Successfully!");
    }
}