package io.github.egorshramko.booking.service;

import io.github.egorshramko.booking.exception.EmptyRequiredFieldException;
import io.github.egorshramko.booking.model.domain.Movie;
import org.springframework.data.domain.Page;

import java.nio.file.NoSuchFileException;
import java.util.List;

/**
 * Интерфейс сервиса для управления списком фильмов на сервере
 */
public interface MovieService {

    /**
     * Метод добавления фильма на сервер
     * @param movie - данные о добавляемом фильме
     * @return Возвращает созданный экземпляр сущности фильма
     * @throws NoSuchFileException в случае, если постер фильма не был загружен в хранилище
     */
    Movie addMovie(Movie movie) throws NoSuchFileException;

    /**
     * Метод удаления фильма с сервера
     * @param movieId - идентификатор фильма на сервере
     */
    void removeMovie(Long movieId);

    /**
     * Метод редактирования информации о фильме
     * @param movie - данные фильма, которые необходимо применить
     * @return Возвращает отредактированную сущность фильма
     */
    Movie editMovie(Movie movie) throws EmptyRequiredFieldException;

    /**
     * Метод получения фильма из базы данных по ID
     * @param movieId - ID фильма
     * @return Возвращает сущность запрошенного фильма
     */
    Movie getMovieById(Long movieId);

    /**
     * Метод получения всех фильмов, находящихся в прокате
     * @param pageNumber - номер страницы списка фильмов
     * @return Возвращает список фильмов, находящихся в прокате
     */
    Page<Movie> getMoviesInRental(Integer pageNumber);
}
