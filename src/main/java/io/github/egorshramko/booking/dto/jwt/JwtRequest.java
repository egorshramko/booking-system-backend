package io.github.egorshramko.booking.dto.jwt;

public record JwtRequest(
        String username,
        String password
) {
}
