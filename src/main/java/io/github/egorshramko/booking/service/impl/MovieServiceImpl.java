package io.github.egorshramko.booking.service.impl;

import io.github.egorshramko.booking.exception.EmptyRequiredFieldException;
import io.github.egorshramko.booking.model.domain.Movie;
import io.github.egorshramko.booking.repository.domain.MovieRepository;
import io.github.egorshramko.booking.service.ImageService;
import io.github.egorshramko.booking.service.MovieService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadObjectRequest;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;

import java.nio.file.NoSuchFileException;
import java.util.Optional;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ImageService imageService;

    @Override
    @Transactional
    public Movie addMovie(Movie movie) throws NoSuchFileException {

        //Проверка, что изображение постера было загружено в S3-хранилище
        String moviePosterImage = movie.getPosterFilename();
        log.info("Checking poster file uploading");
        if (imageService.imageIsUploaded(moviePosterImage)) {

            //устанавливаем актуальность фильма для базы
            movie.setActual(true);

            //проверка, что фильма с аналогичными данными нет в базе
            Optional<Movie> movieOptional = movieRepository.findMovieByParams(
                    movie.getName(),
                    movie.getReleaseYear(),
                    movie.getAgeLimit());

            if (movieOptional.isEmpty()) {
                log.info("Movie not found in database. Creating");
                Movie createdMovie = movieRepository.save(movie);

                log.info("Created movie: {}", createdMovie);
                return createdMovie;
            }
            else {
                Movie movieEntity = movieOptional.get();
                log.info("Movie is found in database. Checking relevance");

                //если фильм был в базе, но был удален ранее, то восстанавливаем его
                // и устанавливаем новое изображение постера
                if (movieEntity.getActual() == false) {
                    log.info("Movie is not relevant. Restore and update");

                    movieEntity.setActual(true);
                    movieEntity.setPosterFilename(movie.getPosterFilename());

                    Movie createdMovie = movieRepository.save(movieEntity);

                    log.info("Created movie: {}", createdMovie);
                    return createdMovie;

                }
                else {
                    log.warn("Such movie is already exists in database");
                    throw new RuntimeException("Such movie is already exists in database");
                }
            }

        }
        else {
            log.warn("Poster image file was not uploaded to S3");
            throw new NoSuchFileException("Poster image file was not uploaded to S3");
        }
    }

    @Override
    @Transactional
    public void removeMovie(Long movieId) {

        log.info("Removing movie with id {}", movieId);

        //Поиск фильма для удаления
        Optional<Movie> movieOptional = movieRepository.findByIdAndActualIsTrue(movieId);

        if (movieOptional.isPresent()) {
            log.info("Movie is found. Removing");
            Movie movieToRemove = movieOptional.get();
            movieToRemove.setActual(false);
            movieRepository.save(movieToRemove);
        }
        else {
            log.info("Movie is not found");
        }

    }

    @Override
    @Transactional
    public Movie editMovie(Movie movie) throws EmptyRequiredFieldException {

        log.info("Edit movie");
        log.info("Input data validating");

        //Валидация входных данных
        if (movie.getId() == null) {
            log.warn("Movie id is empty");
            throw new EmptyRequiredFieldException("Movie id is empty");
        }

        //Поиск фильма для изменения
        log.info("Data validated. Searching movie entity with id {}", movie.getId());
        Optional<Movie> movieOptional = movieRepository.findByIdAndActualIsTrue(movie.getId());
        if (movieOptional.isEmpty()) {
            log.warn("Movie with id {} not found", movie.getId());
            throw new EntityNotFoundException("Movie with id " + movie.getId() + " not found");
        }

        //Изменение и сохранение в БД
        movie.setActual(true);
        Movie savedMovie = movieRepository.save(movie);
        log.info("Saved movie: {}", savedMovie);

        return savedMovie;
    }

    @Override
    public Movie getMovieById(Long movieId) {

        return movieRepository.findByIdAndActualIsTrue(movieId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Movie with id " + movieId + " not found"));
    }

    @Override
    public Page<Movie> getMoviesInRental(Integer pageNumber) {
        return movieRepository.findAllByActualIsTrue(PageRequest.of(pageNumber, 20,
                Sort.by("name").ascending()));
    }

}
