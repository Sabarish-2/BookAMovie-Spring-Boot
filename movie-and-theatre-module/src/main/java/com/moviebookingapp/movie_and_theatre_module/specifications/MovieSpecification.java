package com.moviebookingapp.movie_and_theatre_module.specifications;

import org.springframework.data.jpa.domain.Specification;

import com.moviebookingapp.movie_and_theatre_module.entities.Movie;

/**
 * Specification class for querying movies based on dynamic criteria.
 * This class provides methods to create specifications for filtering movies
 * by their name and theatre name.
 */
public class MovieSpecification {

    /**
     * Creates a specification to filter movies by their name.
     *
     * @param movieName The name of the movie to filter by.
     * @return A specification for filtering movies by name.
     */
    public static Specification<Movie> hasMovieName(String movieName) {
        return (root, query, cb) -> {
            if (movieName == null || movieName.isBlank())
                return null;
            return cb.like(cb.lower(root.get("movieAndTheatre").get("movieName")), movieName.toLowerCase() + "%");
        };
    }

    /**
     * Creates a specification to filter movies by their theatre name.
     *
     * @param theatreName The name of the theatre to filter by.
     * @return A specification for filtering movies by theatre name.
     */
    public static Specification<Movie> hasTheatreName(String theatreName) {
        return (root, query, cb) -> {
            if (theatreName == null || theatreName.isBlank())
                return null;
            return cb.like(cb.lower(root.get("movieAndTheatre").get("theatreName")), theatreName.toLowerCase() + "%");
        };
    }
}