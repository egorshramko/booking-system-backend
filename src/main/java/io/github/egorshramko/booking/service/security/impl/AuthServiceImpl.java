package io.github.egorshramko.booking.service.security.impl;

import io.github.egorshramko.booking.client.RefreshTokenRedisClient;
import io.github.egorshramko.booking.dto.jwt.JwtRequest;
import io.github.egorshramko.booking.dto.jwt.JwtResponse;
import io.github.egorshramko.booking.model.security.User;
import io.github.egorshramko.booking.repository.security.UserRepository;
import io.github.egorshramko.booking.service.security.AuthService;
import io.github.egorshramko.booking.service.security.UserEntityService;
import io.github.egorshramko.booking.utils.provider.JwtProvider;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RefreshTokenRedisClient redisClient;

    @Override
    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        log.debug("login method started");

        final User user = (User) userService.loadUserByUsername(authRequest.username());

        log.debug("credentials: ");
        log.debug("username: {}", authRequest.username());
        log.debug("password: {}", authRequest.password());
        log.debug("user: {}", user);

        if (authRequest.password() != null && passwordEncoder.matches(authRequest.password(), user.getPassword())) {

            log.debug("Password correct");

            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);

            //помещаем рефреш-токен в кеш на 30 дней
            redisClient.setRefreshToken(user.getUsername(), refreshToken);
            log.info("refresh token created and stored");

            return new JwtResponse(accessToken, refreshToken);
        }
        else {
            throw new AuthException("Incorrect password");
        }
    }

    @Override
    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String username = claims.getSubject();
            final String savedRefreshToken = redisClient.getRefreshToken(username);
            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                final User user = userRepository.findByUsernameAndActualIsTrue(username)
                        .orElseThrow(() -> new AuthException("Username not found"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    @Override
    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String username = claims.getSubject();
            final String savedRefreshToken = redisClient.getRefreshToken(username);
            if (savedRefreshToken != null && savedRefreshToken.equals(refreshToken)) {
                final User user = userRepository.findByUsernameAndActualIsTrue(username)
                        .orElseThrow(() -> new AuthException("Username not found"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                redisClient.setRefreshToken(username, newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);

            }
        }
        throw new AuthException("Invalid JWT token");
    }
}
