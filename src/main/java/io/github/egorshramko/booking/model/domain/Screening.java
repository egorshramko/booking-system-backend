package io.github.egorshramko.booking.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Сущность показа фильма в кинотеатре
 * Поля:
 * actual - актуальность
 * cinema - кинотеатр, в котором проходит показ
 * movie - фильм, который показывают
 * startDate - дата начала показа
 * endDate - дата завершения показа
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Screening {

    @Id
    @SequenceGenerator(name = "screening_id_gen", sequenceName = "screening_pkey_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "screening_id_gen")
    private Long id;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private Boolean actual;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cinema cinema;

    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    private LocalDate startDate;
    private LocalDate endDate;

}
