package io.github.egorshramko.booking.dto.admin;

import java.util.Set;

public record AdminUpdateUserRequestUserDto(
        String username,
        String password,
        Set<String> roles
) {
}
