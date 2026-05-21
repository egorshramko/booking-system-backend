package io.github.egorshramko.booking.model.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;

/**
 * Сущность разрешений.
 * Описывает разрешения для определенных объектов системы
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permission implements GrantedAuthority {

    @Id
    @SequenceGenerator(name = "permission_id_gen", sequenceName = "permission_pkey_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_id_gen")
    private Long id;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private Boolean actual;

    private String name;

    private PermissionType type;

    private ObjectType object;

    @Override
    public @Nullable String getAuthority() {
        return name;
    }
}
