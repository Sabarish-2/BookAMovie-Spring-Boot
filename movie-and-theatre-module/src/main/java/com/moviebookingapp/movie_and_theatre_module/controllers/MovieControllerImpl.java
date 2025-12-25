package com.moviebookingapp.movie_and_theatre_module.controllers;

import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.exceptions.MovieNotFoundException;
import com.moviebookingapp.movie_and_theatre_module.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1.0/moviebooking")
public class MovieControllerImpl {

    @Autowired
    private MovieService movieService;

//    @Autowired
//    public void setMovieService(MovieService movieService) {
//        this.movieService = movieService;
//    }
//    public MovieControllerImpl(MovieService movieService) {
//        this.movieService = movieService;
//    }

    @GetMapping("all")
    ResponseEntity<List<MovieDTO>> viewAllMovies() throws MovieNotFoundException {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }
}