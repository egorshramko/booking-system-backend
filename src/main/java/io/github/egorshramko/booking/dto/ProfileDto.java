package io.github.egorshramko.booking.dto;

import java.time.LocalDate;

public record ProfileDto(
        String lastName,
        String firstName,
        LocalDate birthDate

) {
}
