package com.moviebookingapp.movie_and_theatre_module.services;

import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.dtos.UpdateMovieDTO;
import com.moviebookingapp.movie_and_theatre_module.entities.Movie;
import com.moviebookingapp.movie_and_theatre_module.entities.MovieAndTheater;
import com.moviebookingapp.movie_and_theatre_module.exceptions.MovieAlreadyExistsException;
import com.moviebookingapp.movie_and_theatre_module.exceptions.MovieNotFoundException;
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

    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper mapper) {
        this.movieRepository = movieRepository;
        this.mapper = mapper;
    }

    public MovieDTO addMovie(MovieDTO movieDTO) throws MovieAlreadyExistsException {
        Movie newMovie = mapper.map(movieDTO);

        if (movieRepository.findById(newMovie.getMovieAndTheatre()).isPresent()) {
            throw new MovieAlreadyExistsException("Movie " + movieDTO.getMovieName() + " at " + movieDTO.getTheatreName() + " Already Exists!");
        }

        Movie savedMovie = movieRepository.save(newMovie);
        return mapper.map(savedMovie);
    }

    public List<MovieDTO> getAllMovies() throws MovieNotFoundException {
        List<Movie> movies = movieRepository.findAll();
        if (movies.isEmpty()) {
            throw new MovieNotFoundException();
        }
//        TODO: Add Available Seats and Status (Overridden or Calculate) From Order Service.

        return movies.stream().map(mapper::map).toList();
    }

    public List<MovieDTO> searchMovies(String movieName, String theatreName) throws MovieNotFoundException {
//        TODO: Add Available Seats and Status (Overridden or Calculate).
        Specification<Movie> movieSpecification = Specification
                .where(MovieSpecification.hasMovieName(movieName))
                .and(MovieSpecification.hasTheatreName(theatreName));
        List<Movie> movies = movieRepository.findAll(movieSpecification);
        if (movies.isEmpty()) {
            throw new MovieNotFoundException();
        }

        return movies.stream().map(mapper::map).toList();
    }

    public MovieDTO getMovieByID(String movieName, String theatreName) throws MovieNotFoundException {
        Movie movie = movieRepository.findById(new MovieAndTheater(movieName, theatreName))
                .orElseThrow(() -> new MovieNotFoundException(movieName, theatreName));
//        TODO: Add Available Seats and Status (Overridden or Calculate).

        return mapper.map(movie);
    }


    public MovieDTO updateMovie(String movieName, String theatreName, UpdateMovieDTO updateMovieDTO) throws MovieNotFoundException {
        Movie movie = movieRepository.findById(new MovieAndTheater(movieName, theatreName))
                .orElseThrow(() -> new MovieNotFoundException(movieName, theatreName));
        if (updateMovieDTO.getAdminOverrideStatus() != null
                // TODO: && check avl seats for sold out condition
        ) {
//            throw new InvalidMovieStatusException("Movie is already Sold Out, Cannot mark as " + movieStatus);
            movie.setAdminOverrideStatus(updateMovieDTO.getAdminOverrideStatus());   // Should we?
        }
        if (updateMovieDTO.getTicketsAllotted() != null) {
            movie.setTicketsAllotted(updateMovieDTO.getTicketsAllotted());
        }
        movieRepository.save(movie);
        return mapper.map(movie);
    }

//    public MovieDTO setStatus(String movieName, String theatreName, MovieStatus movieStatus) throws MovieNotFoundException {
//        Movie movie = movieRepository.findById(new MovieAndTheater(movieName, theatreName))
//                .orElseThrow(() -> new MovieNotFoundException(movieName, theatreName));
//        if (movieStatus != null
//                // TODO: && check avl seats for sold out condition
//        ) {
////            throw new InvalidMovieStatusException("Movie is already Sold Out, Cannot mark as " + movieStatus);
//        }
//        movie.setAdminOverrideStatus(movieStatus);
//        movieRepository.save(movie);
//        return mapper.map(movie);
//    }

    public void deleteMovie(String movieName, String theatreName) throws MovieNotFoundException {
        Optional<Movie> optionalMovie = movieRepository.findById(new MovieAndTheater(movieName, theatreName));
        if (optionalMovie.isEmpty()) {
            throw new MovieNotFoundException(movieName, theatreName);
        }
        movieRepository.delete(optionalMovie.get());
    }
}
