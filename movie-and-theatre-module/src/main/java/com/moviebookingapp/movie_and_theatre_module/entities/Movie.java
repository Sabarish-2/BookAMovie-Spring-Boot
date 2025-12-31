package com.moviebookingapp.movie_and_theatre_module.entities;

import com.moviebookingapp.movie_and_theatre_module.enums.MovieStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @EmbeddedId
    private MovieAndTheater movieAndTheatre;

    @Min(1)
    @Column(nullable = false)
    private int ticketsAllotted;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MovieStatus adminOverrideStatus;


    public Movie(MovieAndTheater movieAndTheatre, int ticketsAllotted) {
        this.ticketsAllotted = ticketsAllotted;
        this.movieAndTheatre = movieAndTheatre;
    }
}