package io.github.egorshramko.booking.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Сущность роли
 * Используется для предоставления различных доступов к системе
 */
@Entity
@Table(name = "role_")
public class Role {

    @Id
    @SequenceGenerator(name = "role_id_gen", sequenceName = "role_pkey_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_gen")
    private Long id;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private String name;


}
