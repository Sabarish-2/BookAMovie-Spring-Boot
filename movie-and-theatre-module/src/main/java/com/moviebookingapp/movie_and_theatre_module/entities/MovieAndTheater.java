package com.moviebookingapp.movie_and_theatre_module.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MovieAndTheater {
    private String movieName;
    private String theatreName;
}