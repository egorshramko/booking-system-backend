package io.github.egorshramko.booking.utils.provider;

import io.github.egorshramko.booking.model.security.Permission;
import io.github.egorshramko.booking.model.security.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {

    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;

    public JwtProvider(@Value("${jwt.secret.access}") String jwtAccessSecret,
                       @Value("${jwt.secret.refresh}") String jwtRefreshSecret) {

        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
    }

    public String generateAccessToken(@NonNull User user) {

        Set<String> permissions = user.getRoles().stream()
                .flatMap((role) -> role.getPermissions().stream())
                .map(Permission::getAuthority)
                .collect(Collectors.toSet());


        LocalDateTime now = LocalDateTime.now();
        Instant accessExpirationInstant = now.plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant();
        Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("permissions", permissions)
                .compact();
    }

    public String generateRefreshToken(@NonNull User user) {
        LocalDateTime now = LocalDateTime.now();
        Instant refreshExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        Date refreshExpiration = Date.from(refreshExpirationInstant);

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    public boolean validateAccessToken(@NonNull String token) {
        return validateToken(token, jwtAccessSecret);
    }

    public boolean validateRefreshToken(@NonNull String token) {
        return validateToken(token, jwtRefreshSecret);
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    private boolean validateToken(@NonNull String token, @NonNull SecretKey secret) {
        try {
            Jwts.parser()
                    .verifyWith(secret)
                    .build()
                    .parseSignedClaims(token);
            return true;
        }
        catch (ExpiredJwtException expiredJwtException) {
            log.error("Token expired", expiredJwtException);
        }
        catch (UnsupportedJwtException unsupportedJwtException) {
            log.error("Unsupported JWT", unsupportedJwtException);
        }
        catch (MalformedJwtException malformedJwtException) {
            log.error("Malformed JWT", malformedJwtException);
        }
        catch (SignatureException signatureException) {
            log.error("Invalid signature", signatureException);
        }
        catch (Exception e) {
            log.error("Invalid token", e);
        }
        return false;
    }

    private Claims getClaims(@NonNull String token, @NonNull SecretKey secretKey) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }



}
