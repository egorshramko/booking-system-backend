package io.github.egorshramko.booking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность пользователя
 * Предназначена для аутентификации и авторизации в системе
 * Также к данной сущности привязан профиль пользователя
 */
@Entity
@Table(name = "user_")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @SequenceGenerator(name = "user_id_gen", sequenceName = "user_pkey_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
    private Long id;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private String login;

    private String password;

    @ManyToMany
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name = "user_"),
        inverseJoinColumns = @JoinColumn(name = "role_"))
    private Set<Role> roles = new HashSet<>();
}
