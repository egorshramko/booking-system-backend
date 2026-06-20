package io.github.egorshramko.booking.dto.admin;

import java.time.LocalDate;

public record AdminGetUserResponseProfile(
        String lastName,
        String firstName,
        LocalDate birthDate
) {
}
