package com.moviebookingapp.movie_and_theatre_module.controllers;

import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.dtos.UpdateMovieDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@ApiResponse(responseCode = "401", description = "Authentication Error")
@ApiResponse(responseCode = "503", description = "Required Tickets Microservice Unreachable")
@ApiResponse(responseCode = "500", description = "Unexpected Error Internally")
public interface MovieController {

    @Operation(summary = "Retrieve All Movies", method = "Movie Controller")
    @ApiResponse(responseCode = "200", description = "All Movies Retrieved Successfully")
    @ApiResponse(responseCode = "404", description = "No Movies Retrieved")
    ResponseEntity<List<MovieDTO>> viewAllMovies();

    @Operation(summary = "Retrieve Movie By Movie Name and Theatre Name", method = "Movie Controller")
    @ApiResponse(responseCode = "200", description = "Movie Retrieved Successfully")
    @ApiResponse(responseCode = "404", description = "No Movie Found With Given Details")
    ResponseEntity<MovieDTO> getMovieByNameAndTheatre(@PathVariable String movieName, @PathVariable String theatreName);

    @Operation(summary = "Create A New Movie")
    @ApiResponse(responseCode = "201", description = "Movie Created Successfully")
    @ApiResponse(responseCode = "400", description = "Validation Error in Movie Details Provided")
    @ApiResponse(responseCode = "409", description = "Movie Already Exists")
    ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody MovieDTO movieDTO);

    @Operation(summary = "Update an Existing Movie")

    @ApiResponse(responseCode = "200", description = "Movie updated Successfully")
    @ApiResponse(responseCode = "406", description = "Invalid Movie Status or Tickets Allotted Provided")
    @ApiResponse(responseCode = "404", description = "Movie Not Found")
    ResponseEntity<MovieDTO> updateMovie(@PathVariable String movieName, @PathVariable String theatreName, @RequestBody UpdateMovieDTO updateMovieDTO);

    @Operation(summary = "Search Movies")
    @ApiResponse(responseCode = "200", description = "Similar or Same Movies Found")
    @ApiResponse(responseCode = "404", description = "No Movies Found")
    ResponseEntity<List<MovieDTO>> searchMovies(@RequestParam(required = false) String movieName, @RequestParam(required = false) String theatreName);

    @Operation(summary = "Delete An Existing Movie")
    @ApiResponse(responseCode = "200", description = "Movie Deleted Successfully")
    @ApiResponse(responseCode = "404", description = "Movie Not Found")
    ResponseEntity<String> deleteMovie(@PathVariable String movieName, @PathVariable String theatreName);
}
