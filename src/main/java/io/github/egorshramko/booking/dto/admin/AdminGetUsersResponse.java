package io.github.egorshramko.booking.dto.admin;

import java.util.List;

public record AdminGetUsersResponse(
        List<AdminGetUsersResponseUserDto> users
) {
}
