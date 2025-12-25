package com.bam.movie_and_theatre_module.repositories;

import com.bam.movie_and_theatre_module.entities.Movie;
import com.bam.movie_and_theatre_module.entities.MovieAndTheater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, MovieAndTheater> {
}