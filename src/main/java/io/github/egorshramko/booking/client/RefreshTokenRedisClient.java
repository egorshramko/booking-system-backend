package io.github.egorshramko.booking.client;

public interface RefreshTokenRedisClient {

    void setRefreshToken(String username, String token);
    String getRefreshToken(String username);

}
