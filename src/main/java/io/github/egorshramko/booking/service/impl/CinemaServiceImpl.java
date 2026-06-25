package io.github.egorshramko.booking.service.impl;

import io.github.egorshramko.booking.exception.EmptyRequiredFieldException;
import io.github.egorshramko.booking.model.domain.Cinema;
import io.github.egorshramko.booking.model.domain.Employee;
import io.github.egorshramko.booking.repository.domain.CinemaRepository;
import io.github.egorshramko.booking.service.CinemaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;

    public CinemaServiceImpl(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    @Override
    @Transactional
    public Cinema addCinema(Cinema cinema) throws EmptyRequiredFieldException {

        log.info("Called addCinema method");

        //Валидация входных данных
        validateRequiredFields(cinema);

        //Поиск кинотеатра в базе (возможно, он был ранее удален)
        Optional<Cinema> cinemaOptional = cinemaRepository.findByNameAndAddress(
                cinema.getName(),
                cinema.getAddress());

        //Если кинотеатр был ранее удален, то его нужно восстановить без схемы посадки
        if (cinemaOptional.isPresent()) {
            log.info("Restoring old cinema record");

            Cinema restoredCinemaRecord = cinemaOptional.get();
            restoredCinemaRecord.setSeatingChartJson(null);
            restoredCinemaRecord.setActual(true);

            Cinema savedCinema = cinemaRepository.save(restoredCinemaRecord);

            log.info("Restored: {}", savedCinema);
            return savedCinema;

        }
        //Иначе создаем новую запись о кинотеатре
        else {
            log.info("Creating new cinema record");

            cinema.setActual(true);
            Cinema createdCinemaRecord = cinemaRepository.save(cinema);

            log.info("Created: {}", createdCinemaRecord);
            return createdCinemaRecord;
        }
    }

    @Override
    public Cinema getCinemaById(Long cinemaId) {
        return cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new EntityNotFoundException("Cinema with id " + cinemaId + " not found"));
    }

    @Override
    @Transactional
    public Cinema editCinema(Cinema cinema) throws EmptyRequiredFieldException {

        log.info("Starts editing cinema");

        log.info("Validating required fields");
        if (cinema.getId() == null) {
            log.warn("Empty cinema ID for editing");
            throw new EmptyRequiredFieldException("Empty cinema ID for editing");
        }

        log.info("Fields validated successfully");

        Cinema editedCinema = cinemaRepository.findById(cinema.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Cinema entity with id " + cinema.getId() + " not found"));

        //Если кинотеатр удален из системы, то выбрасываем исключение
        if (editedCinema.getActual()) {

            //Обновляем заполненные поля входной сущности
            if (cinema.getAddress() != null) {
                editedCinema.setAddress(cinema.getAddress());
            }
            if (cinema.getName() != null) {
                editedCinema.setName(cinema.getName());
            }
            if (cinema.getCity() != null) {
                editedCinema.setCity(cinema.getCity());
            }

            Cinema savedCinema = cinemaRepository.save(editedCinema);
            log.info("Saved cinema: {}", savedCinema);

            return savedCinema;

        }
        else {
            log.warn("Cinema entity with id {} not found", cinema.getId());
            throw new EntityNotFoundException("Cinema entity with id " + cinema.getId() + " not found");
        }
    }

    @Override
    @Transactional
    public void removeCinema(Long cinemaId) {
        log.info("Starts removing cinema with id {}", cinemaId);

        Optional<Cinema> cinemaOptional = cinemaRepository.findById(cinemaId);
        if (cinemaOptional.isPresent()) {
            log.info("Cinema with id {} found", cinemaId);
            Cinema removingCinema = cinemaOptional.get();

            if (removingCinema.getActual()) {
                log.info("Cinema with id {} is actual. De-actualization", cinemaId);
                removingCinema.setActual(false);
                cinemaRepository.save(removingCinema);
                log.info("De-actualize successful");
            }
            else {
                log.info("Cinema with id {} is already not actual. Do nothing", cinemaId);
            }
        }
        else {
            log.info("Cinema with id {} not found. Do nothing", cinemaId);
        }
    }

    @Override
    public Page<Cinema> getCinemasPage(Integer pageNumber) {
        return cinemaRepository.findAllByActualIsTrue(
                PageRequest.of(pageNumber, 20, Sort.by("city", "name").ascending()));
    }

    @Override
    @Transactional
    public void setSeatingChartJson(Long cinemaId, String seatingChartJson) {
        log.info("Starts seating chart JSON updating");
        log.info("Cinema ID: {}", cinemaId);

        Cinema editedCinema = cinemaRepository.findByIdAndActualIsTrue(cinemaId)
                .orElseThrow(() -> new EntityNotFoundException("Cinema with id " + cinemaId + " not found"));

        editedCinema.setSeatingChartJson(seatingChartJson);
        cinemaRepository.save(editedCinema);
        log.info("Seating chart JSON updating successfully. Cinema ID: {}", cinemaId);
    }

    @Override
    @Transactional
    public void appointManager(Long cinemaId, Employee manager) {
        log.info("Starts appointing cinema manager");

        Cinema cinema = cinemaRepository.findByIdAndActualIsTrue(cinemaId)
                .orElseThrow(() -> new EntityNotFoundException("Cinema with id " + cinemaId + " not found"));

        cinema.setManager(manager);
        cinemaRepository.save(cinema);

        log.info("Appointing manager to cinema ends successful");

    }

    private void validateRequiredFields(Cinema cinema) throws EmptyRequiredFieldException {
        log.info("Data validation starts");

        if (cinema.getName() == null) {
            log.warn("Field \"name\" in Cinema object is empty");
            throw new EmptyRequiredFieldException("Field name in cinema entity is empty");
        }
        if (cinema.getAddress() == null) {
            log.warn("Field \"address\" in Cinema object is empty");
            throw new EmptyRequiredFieldException("Field address in cinema entity is empty");
        }
        if (cinema.getCity() == null) {
            log.warn("Field \"city\" in Cinema object is empty");
            throw new EmptyRequiredFieldException("Field city in cinema entity is empty");
        }

        log.info("Data validated successful");
    }
}
