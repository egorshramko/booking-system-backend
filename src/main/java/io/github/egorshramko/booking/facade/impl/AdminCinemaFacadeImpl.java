package io.github.egorshramko.booking.facade.impl;

import io.github.egorshramko.booking.dto.admin.AdminCreateCinemaRequest;
import io.github.egorshramko.booking.dto.admin.AdminCreateCinemaResponse;
import io.github.egorshramko.booking.facade.AdminCinemaFacade;
import io.github.egorshramko.booking.repository.dictionaries.CityRepository;
import io.github.egorshramko.booking.repository.domain.EmployeeRepository;
import io.github.egorshramko.booking.service.CinemaService;
import io.github.egorshramko.booking.utils.mapper.CinemaMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdminCinemaFacadeImpl implements AdminCinemaFacade {

    @Autowired
    private CinemaMapper cinemaMapper;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public AdminCreateCinemaResponse createCinema(AdminCreateCinemaRequest request) {

        cinemaMapper.toEntityFromAdminCreateCinemaRequest(request,
                cityRepository,
                employeeRepository);

        return null;
    }
}
