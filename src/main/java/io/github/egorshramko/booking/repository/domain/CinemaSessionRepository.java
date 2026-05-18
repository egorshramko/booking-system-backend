package io.github.egorshramko.booking.repository.domain;

import io.github.egorshramko.booking.model.domain.CinemaSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CinemaSessionRepository extends JpaRepository<CinemaSession, Long> {
}
