package io.github.egorshramko.booking.repository.dictionaries;

import io.github.egorshramko.booking.model.dictionaries.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByCity(String city);

}
