package io.github.egorshramko.booking.model.security;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NonNull;
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
@Builder
@Table(name = "permission_")
public class Permission implements GrantedAuthority {

    @Id
    @SequenceGenerator(name = "permission_id_gen", sequenceName = "permission_pkey_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_id_gen")
    private Long id;

    @Builder.Default
    private final LocalDateTime createdAt = LocalDateTime.now();

    private Boolean actual;

    @NonNull
    @Enumerated(EnumType.STRING)
    private PermissionType type;

    @NonNull
    @Enumerated(EnumType.STRING)
    private ObjectType object;

    @Override
    public @NonNull String getAuthority() {
        return type.toString().toUpperCase() + "_" + object.toString().toUpperCase();
    }
}
