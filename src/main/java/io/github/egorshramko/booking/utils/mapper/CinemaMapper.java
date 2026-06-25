package io.github.egorshramko.booking.utils.mapper;

import io.github.egorshramko.booking.dto.admin.AdminCreateCinemaRequest;
import io.github.egorshramko.booking.model.dictionaries.City;
import io.github.egorshramko.booking.model.domain.Cinema;
import io.github.egorshramko.booking.repository.dictionaries.CityRepository;
import io.github.egorshramko.booking.repository.domain.EmployeeRepository;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CinemaMapper {

    @Mapping(target = "actual", constant = "true")
    @Mapping(target = "city", expression = "java(cityRepository.findByCity(dto.city()).orElse(null))")
    @Mapping(source = "photoFilename", target = "cinemaPhotoFilename")
    @Mapping(target = "manager",
            expression = "java(employeeRepository.findByUsername(dto.managerUsername()).orElse(null))")
    Cinema toEntityFromAdminCreateCinemaRequest(AdminCreateCinemaRequest dto,
                                                @Context CityRepository cityRepository,
                                                @Context EmployeeRepository employeeRepository);

}
