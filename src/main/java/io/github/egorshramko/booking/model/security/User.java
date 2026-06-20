package io.github.egorshramko.booking.model.security;

import jakarta.persistence.*;
import lombok.*;
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
@Builder
public class User implements UserDetails {

    @Id
    @SequenceGenerator(name = "user_id_gen", sequenceName = "user_pkey_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
    private Long id;

    @Builder.Default
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Getter(AccessLevel.NONE)
    private Boolean actual;

    @NonNull
    @Column(unique = true)
    private String username;

    @NonNull
    @Column(length = 2000)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name = "user_"),
        inverseJoinColumns = @JoinColumn(name = "role_"))
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    @Override
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .sorted(Comparator.comparing(Permission::getAuthority))
                .collect(Collectors.toList());
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    public Boolean isActual() {
        return actual;
    }
}
