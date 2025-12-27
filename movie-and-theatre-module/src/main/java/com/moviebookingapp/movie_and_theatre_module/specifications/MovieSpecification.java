package com.moviebookingapp.movie_and_theatre_module.specifications;

import com.moviebookingapp.movie_and_theatre_module.entities.Movie;
import org.springframework.data.jpa.domain.Specification;

public class MovieSpecification {

    public static Specification<Movie> hasMovieName(String movieName) {
        return (root, query, cb) -> {
            if (movieName == null || movieName.isBlank()) return null;
            return cb.like(root.get("movieAndTheatre").get("movieName"), movieName + "%");
        };
    }

    public static Specification<Movie> hasTheatreName(String theatreName) {
        return (root, query, cb) -> {
            if (theatreName == null || theatreName.isBlank()) return null;
            return cb.like(root.get("movieAndTheatre").get("theatreName"), theatreName + "%");
        };
    }
}