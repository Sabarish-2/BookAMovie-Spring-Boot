package com.bam.movie_and_theatre_module.controller;

import com.bam.movie_and_theatre_module.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieControllerImpl {

    @Autowired
    private MovieRepository movieRepository;

    public  MovieControllerImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    ResponseEntity<String> get() {
        return new ResponseEntity<>(movieRepository.findAll().get(1).toString(), HttpStatus.OK);
    }
}