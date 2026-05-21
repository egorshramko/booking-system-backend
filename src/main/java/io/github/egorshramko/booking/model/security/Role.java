package io.github.egorshramko.booking.model.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Сущность роли.
 * Используется для предоставления различных доступов к системе
 */
@Entity
@Table(name = "role_")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @SequenceGenerator(name = "role_id_gen", sequenceName = "role_pkey_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_gen")
    private Long id;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private Boolean actual;

    private String name;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST})
    @JoinTable(name = "role_permission",
        joinColumns = @JoinColumn(name = "role_"),
        inverseJoinColumns = @JoinColumn(name = "permission_"))
    private Set<Permission> permissions = new HashSet<>();

}
