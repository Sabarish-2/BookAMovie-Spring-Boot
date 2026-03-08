package com.moviebookingapp.movie_and_theatre_module.services;

import com.moviebookingapp.movie_and_theatre_module.clients.TicketsClient;
import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.dtos.UpdateMovieDTO;
import com.moviebookingapp.movie_and_theatre_module.entities.Movie;
import com.moviebookingapp.movie_and_theatre_module.entities.MovieAndTheater;
import com.moviebookingapp.movie_and_theatre_module.enums.MovieStatus;
import com.moviebookingapp.movie_and_theatre_module.exception.IncorrectTicketsAllottedException;
import com.moviebookingapp.movie_and_theatre_module.exception.InvalidMovieStatusException;
import com.moviebookingapp.movie_and_theatre_module.exception.MovieAlreadyExistsException;
import com.moviebookingapp.movie_and_theatre_module.exception.MovieNotFoundException;
import com.moviebookingapp.movie_and_theatre_module.mappers.MovieMapper;
import com.moviebookingapp.movie_and_theatre_module.repositories.MovieRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private TicketsClient ticketsClient;
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
        when(ticketsClient.getBookedTickets(any(), any())).thenReturn(ResponseEntity.ok(1L));

        List<MovieDTO> expectedList = List.of(movieDTO);
        List<MovieDTO> actualList = movieService.getAllMovies();

        assertEquals(expectedList, actualList);
    }

    @Test
    @DisplayName("AllMovies-negative-movieNotFound")
    void test_AllMovies_negative_movieNotFound() {
        when(movieRepository.findAll()).thenReturn(List.of());
        assertThrows(MovieNotFoundException.class, movieService::getAllMovies);
    }

    @Test
    @DisplayName("AddMovie-Positive")
    void test_AddMovie_positive() {
        when(movieRepository.findById(movieAndTheater)).thenReturn(Optional.empty());
        when(movieRepository.save(movie)).thenReturn(movie);
        when(mapper.map(movieDTO)).thenReturn(movie);
        when(mapper.map(movie)).thenReturn(movieDTO);

        MovieDTO actualMovie = movieService.addMovie(movieDTO);

        assertEquals(movieDTO, actualMovie);
    }

    @Test
    @DisplayName("AddMovie-Negative-MovieAlreadyExists")
    void test_AddMovie_negative_movieAlreadyExists() {
        when(movieRepository.findById(movieAndTheater)).thenReturn(Optional.of(movie));
        when(mapper.map(movieDTO)).thenReturn(movie);

        assertThrows(MovieAlreadyExistsException.class, () -> movieService.addMovie(movieDTO));
    }

    @Test
    @DisplayName("SearchMovies-Positive")
    void test_SearchMovies_positive() {
        when(movieRepository.findAll(any(Specification.class))).thenReturn(List.of(movie));
        when(mapper.map(movie)).thenReturn(movieDTO);
        when(ticketsClient.getBookedTickets(any(), any())).thenReturn(ResponseEntity.ok(1L));

        List<MovieDTO> actualMovies = movieService.searchMovies(movieName, theatreName);

        assertEquals(List.of(movieDTO), actualMovies);
    }

    @Test
    @DisplayName("SearchMovies-Negative-MovieNotFound")
    void test_SearchMovies_negative_movieNotFound() {
        when(movieRepository.findAll(any(Specification.class))).thenReturn(List.of());

        assertThrows(MovieNotFoundException.class, () -> movieService.searchMovies(movieName, theatreName));
    }

    @Test
    @DisplayName("GetMovieByID-Positive")
    void test_GetMovieByID_positive() {
        when(movieRepository.findById(movieAndTheater)).thenReturn(Optional.of(movie));
        when(mapper.map(movie)).thenReturn(movieDTO);
        when(ticketsClient.getBookedTickets(any(), any())).thenReturn(ResponseEntity.ok(1L));

        MovieDTO actualMovie = movieService.getMovieByID(movieName, theatreName);

        assertEquals(movieDTO, actualMovie);
    }

    @Test
    @DisplayName("GetMovieByID-Negative-MovieNotFound")
    void test_GetMovieByID_negative_movieNotFound() {
        when(movieRepository.findById(movieAndTheater)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieService.getMovieByID(movieName, theatreName));
    }

    @Test
    @DisplayName("GetMovieByID-Negative-FeignRuntimeInPrivateMethod")
    void test_GetMovieByID_negative_feignRuntimeInPrivateMethod() {
        when(movieRepository.findById(movieAndTheater)).thenReturn(Optional.of(movie));
        when(mapper.map(movie)).thenReturn(movieDTO);
        when(ticketsClient.getBookedTickets(movieName, theatreName)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> movieService.getMovieByID(movieName, theatreName));
    }

    @Test
    @DisplayName("UpdateMovie-Positive-TicketsAllotted")
    void test_UpdateMovie_positive_ticketsAllotted() {
        UpdateMovieDTO updateMovieDTO = new UpdateMovieDTO();
        updateMovieDTO.setTicketsAllotted(150);

        when(movieRepository.findById(movieAndTheater)).thenReturn(Optional.of(movie));
        when(ticketsClient.getBookedTickets(movieName, theatreName)).thenReturn(ResponseEntity.ok(50L));
        when(mapper.map(movie)).thenReturn(movieDTO);

        MovieDTO actualMovie = movieService.updateMovie(movieName, theatreName, updateMovieDTO);

        assertEquals(movieDTO, actualMovie);
    }

    @Test
    @DisplayName("UpdateMovie-Positive-StatusOverride")
    void test_UpdateMovie_positive_statusOverride() {
        UpdateMovieDTO updateMovieDTO = new UpdateMovieDTO();
        updateMovieDTO.setAdminOverrideStatus(MovieStatus.SOLD_OUT);

        when(movieRepository.findById(movieAndTheater)).thenReturn(Optional.of(movie));
        when(ticketsClient.getBookedTickets(movieName, theatreName)).thenReturn(ResponseEntity.ok(50L));
        when(mapper.map(movie)).thenReturn(movieDTO);

        MovieDTO actualMovie = movieService.updateMovie(movieName, theatreName, updateMovieDTO);

        assertEquals(movieDTO, actualMovie);
    }

    @Test
    @DisplayName("UpdateMovie-Positive-NullInDTO")
    void test_UpdateMovie_positive_nullInDTO() {
        UpdateMovieDTO updateMovieDTO = new UpdateMovieDTO();

        when(movieRepository.findById(movieAndTheater)).thenReturn(Optional.of(movie));
        when(ticketsClient.getBookedTickets(movieName, theatreName)).thenReturn(ResponseEntity.ok((long)ticketsAllotted));
        when(mapper.map(movie)).thenReturn(movieDTO);

        MovieDTO actualMovie = movieService.updateMovie(movieName, theatreName, updateMovieDTO);

        assertEquals(movieDTO, actualMovie);
    }

    @Test
    @DisplayName("UpdateMovie-Negative-MovieNotFound")
    void test_UpdateMovie_negative_movieNotFound() {
        UpdateMovieDTO updateMovieDTO = new UpdateMovieDTO();

        when(movieRepository.findById(movieAndTheater)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class,
                () -> movieService.updateMovie(movieName, theatreName, updateMovieDTO));
    }

    @Test
    @DisplayName("UpdateMovie-Negative-FeignRuntime")
    void test_UpdateMovie_negative_feignRuntime() {
        UpdateMovieDTO updateMovieDTO = new UpdateMovieDTO();
        when(movieRepository.findById(movieAndTheater)).thenReturn(Optional.of(movie));
        when(ticketsClient.getBookedTickets(movieName, theatreName)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> movieService.updateMovie(movieName, theatreName, updateMovieDTO));

    }

    @Test
    @DisplayName("UpdateMovie-Positive-ErrorInTicketsAllotted")
    void test_UpdateMovie_positive_errorInTicketsAllotted() {
        UpdateMovieDTO updateMovieDTO = new UpdateMovieDTO();
        updateMovieDTO.setTicketsAllotted(2);

        when(movieRepository.findById(movieAndTheater)).thenReturn(Optional.of(movie));
        when(ticketsClient.getBookedTickets(movieName, theatreName)).thenReturn(ResponseEntity.ok(50L));

        assertThrows(IncorrectTicketsAllottedException.class, () -> movieService.updateMovie(movieName, theatreName, updateMovieDTO));
    }

    @Test
    @DisplayName("UpdateMovie-Positive-ErrorInStatusOverride")
    void test_UpdateMovie_positive_errorInStatusOverride() {
        UpdateMovieDTO updateMovieDTO = new UpdateMovieDTO();
        updateMovieDTO.setAdminOverrideStatus(MovieStatus.AVAILABLE);

        when(movieRepository.findById(movieAndTheater)).thenReturn(Optional.of(movie));
        when(ticketsClient.getBookedTickets(movieName, theatreName)).thenReturn(ResponseEntity.ok((long)ticketsAllotted));

        assertThrows(InvalidMovieStatusException.class, () -> movieService.updateMovie(movieName, theatreName, updateMovieDTO));
    }

    @Test
    @DisplayName("DeleteMovie-Positive")
    void test_DeleteMovie_positive() {
        when(movieRepository.findById(movieAndTheater)).thenReturn(Optional.of(movie));

        movieService.deleteMovie(movieName, theatreName);

        verify(movieRepository).delete(movie);
        verify(ticketsClient).deleteTicketsForMovieInTheatre(movieName, theatreName);
    }

    @Test
    @DisplayName("DeleteMovie-Negative-MovieNotFound")
    void test_DeleteMovie_negative_movieNotFound() {
        when(movieRepository.findById(movieAndTheater)).thenReturn(Optional.empty());

        assertThrows(MovieNotFoundException.class, () -> movieService.deleteMovie(movieName, theatreName));
    }
}
