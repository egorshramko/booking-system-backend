package io.github.egorshramko.booking.dto.admin;

import io.github.egorshramko.booking.dto.ProfileDto;

import java.util.Set;

public record AdminUserDataResponse(
        Long id,
        String username,
        Set<String> roles,
        ProfileDto profile
) {
}
