package io.github.egorshramko.booking.service;

import io.github.egorshramko.booking.model.domain.Cinema;
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
    Cinema addCinema(Cinema cinema);

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
    Cinema editCinema(Cinema cinema);

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
    Page<Cinema> getCinemasPage(Pageable pageable);

    /**
     * Метод добавления JSON схемы посадки в кинотеатре
     * @param cinemaId - ID кинотеатра
     * @param seatingChartJson - JSON схемы посадки
     */
    void setSeatingChartJson(Long cinemaId, String seatingChartJson);
}
