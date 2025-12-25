package com.moviebookingapp.movie_and_theatre_module.services;

import com.moviebookingapp.movie_and_theatre_module.dtos.MovieDTO;
import com.moviebookingapp.movie_and_theatre_module.entities.Movie;
import com.moviebookingapp.movie_and_theatre_module.entities.MovieAndTheater;
import com.moviebookingapp.movie_and_theatre_module.enums.MovieStatus;
import com.moviebookingapp.movie_and_theatre_module.exceptions.MovieAlreadyExistsException;
import com.moviebookingapp.movie_and_theatre_module.exceptions.MovieNotFoundException;
import com.moviebookingapp.movie_and_theatre_module.mappers.MovieMapper;
import com.moviebookingapp.movie_and_theatre_module.repositories.MovieRepository;
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
            throw new MovieNotFoundException("No Movies Found!");
        }

        return movies.stream().map(mapper::map).toList();
    }

    public MovieDTO setStatus(String movieName, String theatreName, MovieStatus movieStatus) throws MovieNotFoundException {
        Optional<Movie> optionalMovie = movieRepository.findById(new MovieAndTheater(movieName, theatreName));
        if (optionalMovie.isEmpty()) {
            throw new MovieNotFoundException("Movie " + movieName + " at " + theatreName + " Does Not Exists!");
        }
        Movie movie = optionalMovie.get();
        movie.setAdminOverrideStatus(movieStatus);
        movieRepository.save(movie);
        return mapper.map(movie);
    }

    public void deleteMovie(String movieName, String theatreName) throws MovieNotFoundException {
        Optional<Movie> optionalMovie = movieRepository.findById(new MovieAndTheater(movieName, theatreName));
        if (optionalMovie.isEmpty()) {
            throw new MovieNotFoundException("Movie " + movieName + " at " + theatreName + " Does Not Exists!");
        }
        movieRepository.delete(optionalMovie.get());
    }
}
