package io.github.egorshramko.booking.service;

import io.github.egorshramko.booking.exception.EmptyRequiredFieldException;
import io.github.egorshramko.booking.model.domain.Cinema;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Сервис манипуляций с кинотеатрами
 */
public interface CinemaService {

    /**
     * Метод добавления кинотеатра в систему
     * @param cinema - данные кинотеатра, который необходимо добавить
     * @return Возвращает созданную сущность кинотеатра
     */
    Cinema addCinema(Cinema cinema) throws EmptyRequiredFieldException;

    /**
     * Метод получения кинотеатра по ID
     * @param cinemaId - ID кинотеатра, который необходимо получить
     * @return Возвращает найденный кинотеатр
     */
    Cinema getCinemaById(Long cinemaId);

    /**
     * Метод редактирования кинотеатра
     * @param cinema - данные кинотеатра для редактирования
     * @return Возвращает отредактированную сущность кинотеатра
     */
    Cinema editCinema(Cinema cinema) throws EmptyRequiredFieldException;

    /**
     * Метод удаления кинотеатра из системы по ID
     * @param cinemaId - ID удаляемого кинотеатра
     */
    void removeCinema(Long cinemaId);

    /**
     * Метод получения списка кинотеатров постранично
     * @param pageable - представление страницы кинотеатров
     * @return Возвращает страницу с кинотеатрами
     */
    Page<Cinema> getCinemasPage(Integer pageNumber);

    /**
     * Метод добавления JSON схемы посадки в кинотеатре
     * @param cinemaId - ID кинотеатра
     * @param seatingChartJson - JSON схемы посадки
     */
    void setSeatingChartJson(Long cinemaId, String seatingChartJson);
}
