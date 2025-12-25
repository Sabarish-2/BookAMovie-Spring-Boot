package com.bam.movie_and_theatre_module.repository;

import com.bam.movie_and_theatre_module.entity.Movie;
import com.bam.movie_and_theatre_module.entity.MovieAndTheater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, MovieAndTheater> {
}