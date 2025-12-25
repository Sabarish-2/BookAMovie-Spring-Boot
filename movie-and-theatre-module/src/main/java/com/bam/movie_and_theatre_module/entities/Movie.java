package com.bam.movie_and_theatre_module.entities;

import com.bam.movie_and_theatre_module.enums.MovieStatus;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movie {

    @EmbeddedId
    private MovieAndTheater movieAndTheatre;

    private int ticketsAllotted;

    @Enumerated(EnumType.STRING)
    private MovieStatus adminOverrideStatus;
}