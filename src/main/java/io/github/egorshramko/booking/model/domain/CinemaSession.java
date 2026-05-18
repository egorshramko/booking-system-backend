package io.github.egorshramko.booking.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Сущность сеанса
 * Поля:
 * actual - актуальность
 * screening - показ (какой фильм, в каком кинотеатре)
 * placesLeft - мест осталось
 * sessionDate - дата-время сеанса
 * price - цена билета на сеанс (в копейках)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CinemaSession {

    @Id
    @SequenceGenerator(name = "cin_sess_id_gen", sequenceName = "cin_sess_pkey_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cin_sess_id_gen")
    private Long id;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private Boolean actual;

    @ManyToOne(fetch = FetchType.LAZY)
    private Screening screening;

    private Integer placesLeft;

    private LocalDateTime sessionDate;

    private Integer price;

}
