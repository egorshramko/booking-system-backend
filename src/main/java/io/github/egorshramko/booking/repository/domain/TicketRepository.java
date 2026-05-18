package io.github.egorshramko.booking.repository.domain;

import io.github.egorshramko.booking.model.domain.Ticket;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
}
