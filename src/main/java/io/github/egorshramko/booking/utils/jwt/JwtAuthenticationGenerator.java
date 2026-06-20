package io.github.egorshramko.booking.utils.jwt;

import io.github.egorshramko.booking.model.security.JwtAuthentication;
import io.github.egorshramko.booking.model.security.User;
import io.github.egorshramko.booking.repository.security.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public final class JwtAuthenticationGenerator {

    @Autowired
    private UserRepository userRepository;

    public JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtAuthentication = new JwtAuthentication();

        final String username = claims.getSubject();
        final User user = userRepository.findByUsernameAndActualIsTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        jwtAuthentication.setUser(user);
        return jwtAuthentication;

    }

}
