package io.github.egorshramko.booking.dto.admin;

import java.util.Set;

public record AdminGetUserResponse(
        String username,
        Set<String> roles,
        AdminGetUserResponseProfile profile
) {
}
