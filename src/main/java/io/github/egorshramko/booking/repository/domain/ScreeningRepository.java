package io.github.egorshramko.booking.repository.domain;

import io.github.egorshramko.booking.model.domain.Screening;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreeningRepository extends CrudRepository<Screening, Long> {
}
