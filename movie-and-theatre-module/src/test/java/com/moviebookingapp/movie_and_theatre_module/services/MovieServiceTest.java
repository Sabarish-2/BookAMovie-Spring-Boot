package com.moviebookingapp.movie_and_theatre_module.services;

import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.entities.Movie;
import com.moviebookingapp.movie_and_theatre_module.entities.MovieAndTheater;
import com.moviebookingapp.movie_and_theatre_module.exception.MovieNotFoundException;
import com.moviebookingapp.movie_and_theatre_module.mappers.MovieMapper;
import com.moviebookingapp.movie_and_theatre_module.repositories.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MovieMapper mapper;

    private final String movieName = "World War -I";
    private final String theatreName = "PSR";
    private final int ticketsAllotted = 100;
    private final MovieDTO movieDTO;
    private final Movie movie;
    private final MovieAndTheater movieAndTheater;

    public MovieServiceTest() {
        this.movieDTO = new MovieDTO(movieName, theatreName, ticketsAllotted);
        this.movieAndTheater = new MovieAndTheater(movieName, theatreName);
        this.movie = new Movie(movieAndTheater, ticketsAllotted);
    }

    @Test
    @DisplayName("AllMovies-Positive")
    void test_AllMovies_positive() throws MovieNotFoundException {
        when(movieRepository.findAll()).thenReturn(List.of(movie));
        when(mapper.map(movie)).thenReturn(movieDTO);

        List<MovieDTO> expectedList = List.of(movieDTO);
        List<MovieDTO> actualList = movieService.getAllMovies();

        assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("AllMovies-negative-movieNotFound")
    void test_AllMovies_negative_movieNotFound() {
        when(movieRepository.findAll()).thenReturn(List.of());
        assertThrows(MovieNotFoundException.class, () -> movieService.getAllMovies());
    }
}
