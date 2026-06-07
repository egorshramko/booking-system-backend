package io.github.egorshramko.booking.dto.admin;

import io.github.egorshramko.booking.dto.RoleDto;

public record AdminCreateRoleResponse(
        boolean success,
        String message,
        RoleDto data
) {}
