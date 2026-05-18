package io.github.egorshramko.booking.model.domain;

import io.github.egorshramko.booking.model.Profile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Сущность билета
 * Поля:
 * session - сеанс
 * row - ряд
 * place - место
 * profile - профиль пользователя, забронировавшего билет
 * status - статус билета
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @SequenceGenerator(name = "ticket_id_gen", sequenceName = "ticket_pkey_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticket_id_gen")
    private Long id;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private Boolean actual;

    @ManyToOne(fetch = FetchType.LAZY)
    private CinemaSession session;

    private Integer row;

    private Integer place;

    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profile;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}
