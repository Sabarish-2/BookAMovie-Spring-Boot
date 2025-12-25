package com.bam.movie_and_theatre_module.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
//@AllArgsConstructor
//@NoArgsConstructor
@Embeddable
public class MovieAndTheater {
    private String movieName;
    private String theatreName;
}