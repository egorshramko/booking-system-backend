package io.github.egorshramko.booking.service;

import io.github.egorshramko.booking.model.domain.Movie;

import java.util.List;

/**
 * Интерфейс сервиса для управления списком фильмов на сервере
 */
public interface MovieService {

    /**
     * Метод добавления фильма на сервер
     * @param movie - данные о добавляемом фильме
     * @return Возвращает созданный экземпляр сущности фильма
     */
    Movie addMovie(Movie movie);

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
    Movie editMovie(Movie movie);

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
    List<Movie> getMoviesInRental(Integer pageNumber);
}
