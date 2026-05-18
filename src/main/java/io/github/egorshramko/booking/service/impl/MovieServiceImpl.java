package io.github.egorshramko.booking.service.impl;

import io.github.egorshramko.booking.model.domain.Movie;
import io.github.egorshramko.booking.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Override
    public Movie addMovie(Movie movie) {
        return null;
    }

    @Override
    public void removeMovie(Long movieId) {

    }

    @Override
    public Movie editMovie(Movie movie) {
        return null;
    }

    @Override
    public Movie getMovieById(Long movieId) {
        return null;
    }

    @Override
    public List<Movie> getMoviesInRental(Integer pageNumber) {
        return List.of();
    }
}
