package com.moviebookingapp.movie_and_theatre_module.dtos;

import com.moviebookingapp.movie_and_theatre_module.enums.MovieStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateMovieDTO {

    private Integer ticketsAllotted;

//    private Integer ticketsAvailable;

    private MovieStatus adminOverrideStatus;
}
