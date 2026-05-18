package io.github.egorshramko.booking.model.domain;

import io.github.egorshramko.booking.model.Profile;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Сущность сотрудника
 * Представляет сотрудника кинотеатра в системе
 * actual - актуальность
 * position - наименование должности
 * cinema - ссылка на кинотеатр, к которому привязан сотрудник
 * profile - профиль пользователя
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @SequenceGenerator(name = "employee_id_gen", sequenceName = "employee_pkey_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id_gen")
    private Long id;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private Boolean actual;

    private String position;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cinema cinema;

    @OneToOne(fetch = FetchType.LAZY)
    private Profile profile;

}
