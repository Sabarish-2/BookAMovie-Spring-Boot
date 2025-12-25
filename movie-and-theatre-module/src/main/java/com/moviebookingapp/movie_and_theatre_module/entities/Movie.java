package com.moviebookingapp.movie_and_theatre_module.entities;

import com.moviebookingapp.movie_and_theatre_module.enums.MovieStatus;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Entity
@Data
public class Movie {

    @EmbeddedId
    private MovieAndTheater movieAndTheatre;

    private int ticketsAllotted;

    @Enumerated(EnumType.STRING)
    private MovieStatus adminOverrideStatus;

//    Might be erroneous
    public Movie() {
        this.movieAndTheatre = new MovieAndTheater();
    }

    public Movie(MovieAndTheater movieAndTheatre, int ticketsAllotted) {
        this.ticketsAllotted = ticketsAllotted;
        this.movieAndTheatre = movieAndTheatre;
    }
}