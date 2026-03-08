package com.moviebookingapp.movie_and_theatre_module.controllers;

import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.dtos.UpdateMovieDTO;
import com.moviebookingapp.movie_and_theatre_module.exception.MovieAlreadyExistsException;
import com.moviebookingapp.movie_and_theatre_module.exception.MovieNotFoundException;
import com.moviebookingapp.movie_and_theatre_module.services.MovieService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieControllerImplTest {

    @InjectMocks
    private MovieControllerImpl movieController;

    @Mock
    private MovieService movieService;

    private final String movieName = "World War -I";
    private final String theatreName = "PSR";
    private final int ticketsAllotted = 100;
    private final MovieDTO movieDTO = new MovieDTO(movieName, theatreName, ticketsAllotted);

    @Test
    @DisplayName("ViewAllMovies-Positive")
    void viewAllMovies_positive() {
        when(movieService.getAllMovies()).thenReturn(List.of(movieDTO));

        ResponseEntity<List<MovieDTO>> response = movieController.viewAllMovies();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(movieDTO), response.getBody());
    }

    @Test
    @DisplayName("ViewAllMovies-Negative-MovieNotFound")
    void viewAllMovies_negative_movieNotFound() {
        when(movieService.getAllMovies()).thenThrow(new MovieNotFoundException());

        assertThrows(MovieNotFoundException.class, () -> movieController.viewAllMovies());
    }

    @Test
    @DisplayName("GetMovieByNameAndTheatre-Positive")
    void getMovieByNameAndTheatre_positive() {
        when(movieService.getMovieByID(movieName, theatreName)).thenReturn(movieDTO);

        ResponseEntity<MovieDTO> response = movieController.getMovieByNameAndTheatre(movieName, theatreName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movieDTO, response.getBody());
    }

    @Test
    @DisplayName("GetMovieByNameAndTheatre-Negative-MovieNotFound")
    void getMovieByNameAndTheatre_negative_movieNotFound() {
        when(movieService.getMovieByID(movieName, theatreName))
                .thenThrow(new MovieNotFoundException(movieName, theatreName));

        assertThrows(MovieNotFoundException.class,
                () -> movieController.getMovieByNameAndTheatre(movieName, theatreName));
    }

    @Test
    @DisplayName("CreateMovie-Positive")
    void createMovie_positive() {
        when(movieService.addMovie(movieDTO)).thenReturn(movieDTO);

        ResponseEntity<MovieDTO> response = movieController.createMovie(movieDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(movieDTO, response.getBody());
    }

    @Test
    @DisplayName("CreateMovie-Negative-MovieAlreadyExists")
    void createMovie_negative_movieAlreadyExists() {
        when(movieService.addMovie(movieDTO)).thenThrow(new MovieAlreadyExistsException("exists"));

        assertThrows(MovieAlreadyExistsException.class, () -> movieController.createMovie(movieDTO));
    }

    @Test
    @DisplayName("UpdateMovie-Positive")
    void updateMovie_positive() {
        UpdateMovieDTO updateMovieDTO = new UpdateMovieDTO();
        updateMovieDTO.setTicketsAllotted(150);

        when(movieService.updateMovie(movieName, theatreName, updateMovieDTO)).thenReturn(movieDTO);

        ResponseEntity<MovieDTO> response = movieController.updateMovie(movieName, theatreName, updateMovieDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(movieDTO, response.getBody());
    }

    @Test
    @DisplayName("UpdateMovie-Negative-MovieNotFound")
    void updateMovie_negative_movieNotFound() {
        UpdateMovieDTO updateMovieDTO = new UpdateMovieDTO();

        when(movieService.updateMovie(movieName, theatreName, updateMovieDTO))
                .thenThrow(new MovieNotFoundException(movieName, theatreName));

        assertThrows(MovieNotFoundException.class,
                () -> movieController.updateMovie(movieName, theatreName, updateMovieDTO));
    }

    @Test
    @DisplayName("SearchMovies-Positive")
    void searchMovies_positive() {
        when(movieService.searchMovies(movieName, theatreName)).thenReturn(List.of(movieDTO));

        ResponseEntity<List<MovieDTO>> response = movieController.searchMovies(movieName, theatreName);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(movieDTO), response.getBody());
    }

    @Test
    @DisplayName("SearchMovies-Negative-MovieNotFound")
    void searchMovies_negative_movieNotFound() {
        when(movieService.searchMovies(movieName, theatreName))
                .thenThrow(new MovieNotFoundException(movieName, theatreName));

        assertThrows(MovieNotFoundException.class, () -> movieController.searchMovies(movieName, theatreName));
    }

    @Test
    @DisplayName("DeleteMovie-Positive")
    void deleteMovie_positive() {
        ResponseEntity<String> response = movieController.deleteMovie(movieName, theatreName);

        verify(movieService).deleteMovie(movieName, theatreName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Movie " + movieName + " At " + theatreName + " Deleted Successfully!", response.getBody());
    }

    @Test
    @DisplayName("DeleteMovie-Negative-MovieNotFound")
    void deleteMovie_negative_movieNotFound() {
        doThrow(new MovieNotFoundException(movieName, theatreName))
                .when(movieService).deleteMovie(movieName, theatreName);

        assertThrows(MovieNotFoundException.class, () -> movieController.deleteMovie(movieName, theatreName));
    }
}
