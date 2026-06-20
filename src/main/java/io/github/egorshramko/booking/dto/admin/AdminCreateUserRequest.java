package io.github.egorshramko.booking.dto.admin;

public record AdminCreateUserRequest(
        AdminCreateUserRequestUserDto user,
        AdminCreateUserRequestProfileDto profile
) {
}
