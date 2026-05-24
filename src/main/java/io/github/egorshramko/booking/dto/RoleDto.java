package io.github.egorshramko.booking.dto;

import java.util.Set;

public record RoleDto(
    Long id,
    String name,
    Set<PermissionDto> permissions
) {
}
