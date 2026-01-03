package com.moviebookingapp.movie_and_theatre_module.services;

import com.moviebookingapp.movie_and_theatre_module.Feign.TicketsClient;
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

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper mapper;
    private final TicketsClient ticketsClient;

    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper mapper, TicketsClient ticketsClient) {
        this.movieRepository = movieRepository;
        this.mapper = mapper;
        this.ticketsClient = ticketsClient;
    }

    @Override
    public MovieDTO addMovie(MovieDTO movieDTO) {
        Movie newMovie = mapper.map(movieDTO);

        if (movieRepository.findById(newMovie.getMovieAndTheatre()).isPresent()) {
            throw new MovieAlreadyExistsException("Movie " + movieDTO.getMovieName() + " at " + movieDTO.getTheatreName() + " Already Exists!");
        }

        Movie savedMovie = movieRepository.save(newMovie);
        return mapper.map(savedMovie);
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        List<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) {
            throw new MovieNotFoundException();
        }
        return movies.stream().map(this::addAvailableSeatsAndStatus).toList();
    }

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
        Long bookedTickets = ticketsClient.getBookedTickets(movieDTO.getMovieName(), movieDTO.getTheatreName()).getBody();
        if (bookedTickets == null) {
            throw new RuntimeException("Feign Client Returned NULL!");
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

    @Override
    public MovieDTO getMovieByID(String movieName, String theatreName) {
        Movie movie = movieRepository.findById(new MovieAndTheater(movieName, theatreName))
                .orElseThrow(() -> new MovieNotFoundException(movieName, theatreName));

        return addAvailableSeatsAndStatus(movie);
    }


    @Override
    public MovieDTO updateMovie(String movieName, String theatreName, UpdateMovieDTO updateMovieDTO) {
        Movie movie = movieRepository.findById(new MovieAndTheater(movieName, theatreName))
                .orElseThrow(() -> new MovieNotFoundException(movieName, theatreName));
        Long ticketsBooked = ticketsClient.getBookedTickets(movieName, theatreName).getBody();
        if (ticketsBooked == null) {
            throw new RuntimeException("Feign Client Returned NULL!");
        }
        if (updateMovieDTO.getTicketsAllotted() != null) {
            if (updateMovieDTO.getTicketsAllotted() < ticketsBooked) {
                throw new IncorrectTicketsAllottedException("Tickets Allotted cannot be less than Tickets Booked!");
            }
            movie.setTicketsAllotted(updateMovieDTO.getTicketsAllotted());
        }
        if (updateMovieDTO.getAdminOverrideStatus() != null) {
            if (ticketsBooked == movie.getTicketsAllotted()) {
                throw new InvalidMovieStatusException("Movie is already Sold Out, Cannot mark " + movieName + " at " + theatreName + " as " + updateMovieDTO.getAdminOverrideStatus());
            }
            movie.setAdminOverrideStatus(updateMovieDTO.getAdminOverrideStatus());   // Should we?
        }
        movieRepository.save(movie);
        return mapper.map(movie);
    }

//    @Override
//    public MovieDTO setStatus(String movieName, String theatreName, MovieStatus movieStatus) {
//        Movie movie = movieRepository.findById(new MovieAndTheater(movieName, theatreName))
//                .orElseThrow(() -> new MovieNotFoundException(movieName, theatreName));
//        if (movieStatus != null
//                // TODO: && check avl seats for sold out condition
//        ) {

    /// /            throw new InvalidMovieStatusException("Movie is already Sold Out, Cannot mark as " + movieStatus);
//        }
//        movie.setAdminOverrideStatus(movieStatus);
//        movieRepository.save(movie);
//        return mapper.map(movie);
//    }

    @Override
    public void deleteMovie(String movieName, String theatreName) {
        Optional<Movie> optionalMovie = movieRepository.findById(new MovieAndTheater(movieName, theatreName));
        if (optionalMovie.isEmpty()) {
            throw new MovieNotFoundException(movieName, theatreName);
        }
        ticketsClient.deleteTicketsForTicketInTheatre(movieName, theatreName);
        movieRepository.delete(optionalMovie.get());
    }
}
