package io.github.egorshramko.booking.dto.admin;

import io.github.egorshramko.booking.dto.ProfileDto;

public record AdminUpdateUserRequest(
    AdminUpdateUserRequestUserDto user,
    ProfileDto profile
) {
}
