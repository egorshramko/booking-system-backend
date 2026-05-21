package io.github.egorshramko.booking.model.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сущность пользователя
 * Предназначена для аутентификации и авторизации в системе
 * Также к данной сущности привязан профиль пользователя
 */
@Entity
@Data
@Table(name = "user_")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "user_id_gen", sequenceName = "user_pkey_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
    private Long id;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private Boolean actual;

    private String username;

    private String password;

    @ManyToMany
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name = "user_"),
        inverseJoinColumns = @JoinColumn(name = "role_"))
    private Set<Role> roles = new HashSet<>();

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .sorted(Comparator.comparing(Permission::getName))
                .collect(Collectors.toList());
    }
}
