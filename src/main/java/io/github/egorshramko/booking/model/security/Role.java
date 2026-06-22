package io.github.egorshramko.booking.model.security;

import jakarta.persistence.*;
import lombok.*;

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

    @Builder.Default
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Getter(AccessLevel.NONE)
    private Boolean actual;

    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private RoleType type;

    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(name = "role_permission",
        joinColumns = @JoinColumn(name = "role_"),
        inverseJoinColumns = @JoinColumn(name = "permission_"))
    private Set<Permission> permissions = new HashSet<>();

    public Boolean isActual() {
        return actual;
    }

}
