package io.github.egorshramko.booking.dto.admin;

import io.github.egorshramko.booking.dto.PermissionDto;

import java.util.Set;

public record AdminPermissionsResponse(
        Set<PermissionDto> permissions
) {}
