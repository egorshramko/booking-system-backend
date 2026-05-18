package io.github.egorshramko.booking.service.impl;

import io.github.egorshramko.booking.model.domain.Cinema;
import io.github.egorshramko.booking.repository.domain.CinemaRepository;
import io.github.egorshramko.booking.service.CinemaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;

    public CinemaServiceImpl(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    @Override
    public Cinema addCinema(Cinema cinema) {
        return null;
    }

    @Override
    public Cinema getCinemaById(Long cinemaId) {
        return null;
    }

    @Override
    public Cinema editCinema(Cinema cinema) {
        return null;
    }

    @Override
    public void removeCinema(Long cinemaId) {

    }

    @Override
    public Page<Cinema> getCinemasPage(Pageable pageable) {
        return null;
    }

    @Override
    public void setSeatingChartJson(Long cinemaId, String seatingChartJson) {

    }
}
