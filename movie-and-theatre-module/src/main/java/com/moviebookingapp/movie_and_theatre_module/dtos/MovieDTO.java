package com.moviebookingapp.movie_and_theatre_module.dtos;

import com.moviebookingapp.movie_and_theatre_module.enums.MovieStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MovieDTO {

    @NotBlank(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.movieName.invalid}")
    private String movieName;

    @NotBlank(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.theatreName.invalid}")
    private String theatreName;

    @Min(value = 0L, message = "{com.moviebookingapp.movie_and_theatre_module.dtos.ticketsAllotted.zero}")
    private int ticketsAllotted;

    private Integer ticketsAvailable;

    private MovieStatus movieStatus;

    public MovieDTO(String movieName, String theatreName, int ticketsAllotted) {
        this.movieName = movieName;
        this.theatreName = theatreName;
        this.ticketsAllotted = ticketsAllotted;
    }
}
