package com.moviebookingapp.movie_and_theatre_module.dtos;

import com.moviebookingapp.movie_and_theatre_module.enums.MovieStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

    @Size(min = 1, message = "{com.moviebookingapp.movie_and_theatre_module.dtos.movieName.empty}")
    @NotNull(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.movieName.null}")
    private String movieName;

    @Size(min = 1, message = "{com.moviebookingapp.movie_and_theatre_module.dtos.theatreName.empty}")
    @NotNull(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.theatreName.null}")
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
