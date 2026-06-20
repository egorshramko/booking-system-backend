package io.github.egorshramko.booking.dto.admin;

import java.time.LocalDate;

public record AdminCreateUserRequestProfileDto(
        String lastName,
        String firstName,
        LocalDate birthDate

) {
}
