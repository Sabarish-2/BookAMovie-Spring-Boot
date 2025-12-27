package com.moviebookingapp.movie_and_theatre_module.controllers;

import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.dtos.UpdateMovieDTO;
import com.moviebookingapp.movie_and_theatre_module.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1.0/moviebooking/movies")
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
    public ResponseEntity<MovieDTO> createMovie(MovieDTO movieDTO) {
        return new ResponseEntity<>(movieService.addMovie(movieDTO), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("{movieName}/update/{theatreName}")
    public ResponseEntity<MovieDTO> updateMovie(String movieName, String theatreName, UpdateMovieDTO updateMovieDTO) {
        return ResponseEntity.ok(movieService.updateMovie(movieName, theatreName, updateMovieDTO));
    }

    @Override
    @GetMapping("search")
    public ResponseEntity<List<MovieDTO>> searchMovies(String movieName, String theatreName) {
        return new ResponseEntity<>(movieService.searchMovies(movieName, theatreName), HttpStatus.OK);
    }

    @Override
    @DeleteMapping("{movieName}/delete/{theatreName}")
    public ResponseEntity<String> deleteMovie(String movieName, String theatreName) {
        movieService.deleteMovie(movieName, theatreName);
        return ResponseEntity.ok("Movie " + movieName + " At " + theatreName + " Deleted Successfully!");
    }
}