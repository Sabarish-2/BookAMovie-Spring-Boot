package com.moviebookingapp.movie_and_theatre_module.repositories;

import com.moviebookingapp.movie_and_theatre_module.entities.Movie;
import com.moviebookingapp.movie_and_theatre_module.entities.MovieAndTheater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, MovieAndTheater> {
}