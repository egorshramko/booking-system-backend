package io.github.egorshramko.booking.controller;

import io.github.egorshramko.booking.dto.jwt.JwtRequest;
import io.github.egorshramko.booking.dto.jwt.JwtResponse;
import io.github.egorshramko.booking.dto.jwt.RefreshJwtRequest;
import io.github.egorshramko.booking.service.security.AuthService;
import jakarta.security.auth.message.AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) throws AuthException {
        JwtResponse responseBody = authService.login(authRequest);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("token")
    public ResponseEntity<JwtResponse> token(@RequestBody RefreshJwtRequest refreshJwtRequest) throws AuthException {
        JwtResponse responseBody = authService.getAccessToken(refreshJwtRequest.refreshToken());
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("refresh")
    public ResponseEntity<JwtResponse> refresh(@RequestBody RefreshJwtRequest refreshJwtRequest) throws AuthException {
        JwtResponse responseBody = authService.refresh(refreshJwtRequest.refreshToken());
        return ResponseEntity.ok(responseBody);
    }

}
