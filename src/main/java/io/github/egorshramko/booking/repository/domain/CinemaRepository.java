package io.github.egorshramko.booking.repository.domain;

import io.github.egorshramko.booking.model.domain.Cinema;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    Optional<Cinema> findByNameAndAddress(String name, String address);
    Page<Cinema> findAllByActualIsTrue(Pageable pageable);
    Optional<Cinema> findByIdAndActualIsTrue(Long id);

}
