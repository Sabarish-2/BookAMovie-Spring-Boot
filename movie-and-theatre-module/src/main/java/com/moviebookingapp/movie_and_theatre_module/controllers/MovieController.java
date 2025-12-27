package com.moviebookingapp.movie_and_theatre_module.controllers;

import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.dtos.UpdateMovieDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@ApiResponse(responseCode = "500", description = "Unexpected Error Internally")
public interface MovieController {

    @Operation(summary = "Retrieve All Movies")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "All Movies Retrieved Successfully"),
            @ApiResponse(responseCode = "404", description = "No Movies Retrieved")
    })
    ResponseEntity<List<MovieDTO>> viewAllMovies();

    @Operation(summary = "Create A New Movie")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Movie Created Successfully"),
            @ApiResponse(responseCode = "406", description = "Validation Error in Movie Details Provided"),
            @ApiResponse(responseCode = "409", description = "Movie Already Exists")
    })
    ResponseEntity<MovieDTO> createMovie(@Valid @RequestBody MovieDTO movieDTO);

    @Operation(summary = "Update an Existing Movie")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movie updated Successfully"),
            @ApiResponse(responseCode = "406", description = "Validation Error in Movie Details Provided"),
//            @ApiResponse(responseCode = "400", description = "Invalid Movie Status Provided"),
            @ApiResponse(responseCode = "404", description = "Movie Not Found")
    })
    ResponseEntity<MovieDTO> updateMovie(@NotBlank(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.movieName.invalid}") @PathVariable(required = false) String movieName, @NotBlank(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.theatreName.invalid}") @PathVariable(required = false) String theatreName, UpdateMovieDTO updateMovieDTO) ;

    @Operation(summary = "Search Movies")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Similar or Same Movies Found"),
            @ApiResponse(responseCode = "404", description = "No Movies Found")
    })
    ResponseEntity<List<MovieDTO>> searchMovies(@RequestParam(required = false) String movieName, @RequestParam(required = false) String theatreName);

    @Operation(summary = "Delete An Existing Movie")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movie Deleted Successfully"),
            @ApiResponse(responseCode = "404", description = "Movie Not Found"),
            @ApiResponse(responseCode = "406", description = "Validation Error in Movie Details Provided")
    })
    ResponseEntity<String> deleteMovie(@NotBlank(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.movieName.invalid}") @PathVariable(required = false) String movieName, @NotBlank(message = "{com.moviebookingapp.movie_and_theatre_module.dtos.theatreName.invalid}") @PathVariable(required = false) String theatreName);
}
