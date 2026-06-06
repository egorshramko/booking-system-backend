package io.github.egorshramko.booking.service.security;

import io.github.egorshramko.booking.dto.jwt.JwtRequest;
import io.github.egorshramko.booking.dto.jwt.JwtResponse;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;

public interface AuthService {

    JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException;
    JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException;
    JwtResponse refresh(@NonNull String refreshToken) throws AuthException;

}
