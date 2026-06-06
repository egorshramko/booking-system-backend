package io.github.egorshramko.booking.client.impl;

import io.github.egorshramko.booking.client.RefreshTokenRedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class RefreshTokenRedisClientImpl implements RefreshTokenRedisClient {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${jwt.ttl.refresh}")
    private Integer tokenTTLDays;

    @Override
    public void setRefreshToken(String username, String token) {
        final String refreshTokenKey = "refresh_token:" + username;
        redisTemplate.opsForValue().set(refreshTokenKey, token, Duration.ofDays(tokenTTLDays));
    }

    @Override
    public String getRefreshToken(String username) {
        final String refreshTokenKey = "refresh_token:" + username;
        return redisTemplate.opsForValue().get(refreshTokenKey);
    }
}
