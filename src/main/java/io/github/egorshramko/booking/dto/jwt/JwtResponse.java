package io.github.egorshramko.booking.dto.jwt;

public record JwtResponse(
        String type,
        String accessToken,
        String refreshToken
) {

    public JwtResponse(String accessToken, String refreshToken) {
        this("Bearer", accessToken, refreshToken);
    }

}
