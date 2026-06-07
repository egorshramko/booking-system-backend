package io.github.egorshramko.booking.dto.admin;

import io.github.egorshramko.booking.dto.PermissionDto;

import java.util.List;

public record AdminCreateRoleRequest(
        String name,
        List<PermissionDto> permissions
) {}
