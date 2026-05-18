package io.github.egorshramko.booking.model;

import io.github.egorshramko.booking.model.security.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    @SequenceGenerator(name = "profile_id_gen", sequenceName = "profile_pkey_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_id_gen")
    private Long id;

    private final LocalDateTime createdAt = LocalDateTime.now();

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

}
