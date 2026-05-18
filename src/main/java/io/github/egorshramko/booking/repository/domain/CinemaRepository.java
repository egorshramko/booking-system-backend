package io.github.egorshramko.booking.repository.domain;

import io.github.egorshramko.booking.model.domain.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
