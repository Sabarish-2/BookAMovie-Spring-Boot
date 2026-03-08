package com.moviebookingapp.movie_and_theatre_module.mappers;

import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.entities.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    @Mapping(source = "movieAndTheatre.movieName", target = "movieName")
    @Mapping(source = "movieAndTheatre.theatreName", target = "theatreName")
    MovieDTO map(Movie movie);

    @Mapping(source = "movieName", target = "movieAndTheatre.movieName")
    @Mapping(source = "theatreName", target = "movieAndTheatre.theatreName")
    Movie map(MovieDTO movieDto);
}