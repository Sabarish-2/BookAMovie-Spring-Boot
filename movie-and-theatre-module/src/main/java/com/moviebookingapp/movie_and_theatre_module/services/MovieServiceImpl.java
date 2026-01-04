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
import com.moviebookingapp.movie_and_theatre_module.specifications.MovieSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing movies.
 * This class provides methods to add, update, delete, and retrieve movies.
 * It interacts with the repository layer and external services.
 */
@Service
public class MovieServiceImpl implements MovieService {

    /**
     * Repository for accessing movie data.
     */
    private final MovieRepository movieRepository;

    /**
     * Mapper for converting between DTOs and entities.
     */
    private final MovieMapper mapper;

    /**
     * Client for interacting with the tickets service.
     */
    private final TicketsClient ticketsClient;

    /**
     * Constructor for MovieServiceImpl.
     *
     * @param movieRepository Repository for accessing movie data.
     * @param mapper          Mapper for converting between DTOs and entities.
     * @param ticketsClient   Client for interacting with the tickets service.
     */
    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper mapper, TicketsClient ticketsClient) {
        this.movieRepository = movieRepository;
        this.mapper = mapper;
        this.ticketsClient = ticketsClient;
    }

    /**
     * Adds a new movie to the system.
     *
     * @param movieDTO Data transfer object containing movie details.
     * @return The added movie as a DTO.
     * @throws MovieAlreadyExistsException if the movie already exists.
     */
    @Override
    public MovieDTO addMovie(MovieDTO movieDTO) {
        Movie newMovie = mapper.map(movieDTO);

        if (movieRepository.findById(newMovie.getMovieAndTheatre()).isPresent()) {
            throw new MovieAlreadyExistsException(
                    "Movie " + movieDTO.getMovieName() + " at " + movieDTO.getTheatreName() + " Already Exists!");
        }

        Movie savedMovie = movieRepository.save(newMovie);
        return mapper.map(savedMovie);
    }

    /**
     * Retrieves all movies from the system.
     *
     * @return A list of all movies as DTOs.
     * @throws MovieNotFoundException if no movies are found.
     */
    @Override
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) {
            throw new MovieNotFoundException();
        }
        return movies.stream().map(this::addAvailableSeatsAndStatus).toList();
    }

    /**
     * Searches for movies by name and theatre.
     *
     * @param movieName   The name of the movie.
     * @param theatreName The name of the theatre.
     * @return A list of movies matching the search criteria as DTOs.
     * @throws MovieNotFoundException if no movies are found.
     */
    @Override
    public List<MovieDTO> searchMovies(String movieName, String theatreName) {
        Specification<Movie> movieSpecification = Specification
                .where(MovieSpecification.hasMovieName(movieName))
                .and(MovieSpecification.hasTheatreName(theatreName));
        List<Movie> movies = movieRepository.findAll(movieSpecification);
        if (movies.isEmpty()) {
            throw new MovieNotFoundException();
        }
        return movies.stream().map(this::addAvailableSeatsAndStatus).toList();
    }

    private MovieDTO addAvailableSeatsAndStatus(Movie movie) {
        MovieDTO movieDTO = mapper.map(movie);
        Long bookedTickets = ticketsClient.getBookedTickets(movieDTO.getMovieName(), movieDTO.getTheatreName())
                .getBody();
        if (bookedTickets == null) {
            throw new RuntimeException("clients Client Returned NULL!");
        }
        int allottedTickets = movieDTO.getTicketsAllotted();
        movieDTO.setTicketsAvailable((int) (allottedTickets - bookedTickets));
        if (bookedTickets == allottedTickets) {
            movieDTO.setMovieStatus(MovieStatus.SOLD_OUT);
        } else if (movie.getAdminOverrideStatus() != null) {
            movieDTO.setMovieStatus(movie.getAdminOverrideStatus());
        } else if (bookedTickets >= allottedTickets / 2) {
            movieDTO.setMovieStatus(MovieStatus.BOOK_ASAP);
        } else {
            movieDTO.setMovieStatus(MovieStatus.AVAILABLE);
        }
        return movieDTO;
    }

    /**
     * Retrieves a movie by its ID.
     *
     * @param movieName   The name of the movie.
     * @param theatreName The name of the theatre.
     * @return The movie matching the ID as a DTO.
     * @throws MovieNotFoundException if the movie is not found.
     */
    @Override
    public MovieDTO getMovieByID(String movieName, String theatreName) {
        Movie movie = movieRepository.findById(new MovieAndTheater(movieName, theatreName))
                .orElseThrow(() -> new MovieNotFoundException(movieName, theatreName));

        return addAvailableSeatsAndStatus(movie);
    }

    /**
     * Updates an existing movie.
     *
     * @param movieName      The name of the movie.
     * @param theatreName    The name of the theatre.
     * @param updateMovieDTO Data transfer object containing updated movie details.
     * @return The updated movie as a DTO.
     * @throws MovieNotFoundException            if the movie is not found.
     * @throws IncorrectTicketsAllottedException if the allotted tickets are less
     *                                           than the booked tickets.
     * @throws InvalidMovieStatusException       if the movie status is invalid.
     */
    @Override
    public MovieDTO updateMovie(String movieName, String theatreName, UpdateMovieDTO updateMovieDTO) {
        Movie movie = movieRepository.findById(new MovieAndTheater(movieName, theatreName))
                .orElseThrow(() -> new MovieNotFoundException(movieName, theatreName));
        Long ticketsBooked = ticketsClient.getBookedTickets(movieName, theatreName).getBody();
        if (ticketsBooked == null) {
            throw new RuntimeException("clients Client Returned NULL!");
        }
        if (updateMovieDTO.getTicketsAllotted() != null) {
            if (updateMovieDTO.getTicketsAllotted() < ticketsBooked) {
                throw new IncorrectTicketsAllottedException("Tickets Allotted cannot be less than Tickets Booked!");
            }
            movie.setTicketsAllotted(updateMovieDTO.getTicketsAllotted());
        } else if (updateMovieDTO.getAdminOverrideStatus() != null) {
            if (ticketsBooked == movie.getTicketsAllotted()) {
                throw new InvalidMovieStatusException("Movie is already Sold Out, Cannot mark " + movieName + " at "
                        + theatreName + " as " + updateMovieDTO.getAdminOverrideStatus());
            }
            movie.setAdminOverrideStatus(updateMovieDTO.getAdminOverrideStatus());
        } else {
            movie.setAdminOverrideStatus(null);
        }
        movieRepository.save(movie);
        return addAvailableSeatsAndStatus(movie);
    }

    /**
     * Deletes a movie from the system.
     *
     * @param movieName   The name of the movie.
     * @param theatreName The name of the theatre.
     * @throws MovieNotFoundException if the movie is not found.
     */
    @Override
    public void deleteMovie(String movieName, String theatreName) {
        Optional<Movie> optionalMovie = movieRepository.findById(new MovieAndTheater(movieName, theatreName));
        if (optionalMovie.isEmpty()) {
            throw new MovieNotFoundException(movieName, theatreName);
        }
        ticketsClient.deleteTicketsForMovieInTheatre(movieName, theatreName);
        movieRepository.delete(optionalMovie.get());
    }
}
