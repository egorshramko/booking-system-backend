package io.github.egorshramko.booking.repository.domain;

import io.github.egorshramko.booking.model.domain.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
}
