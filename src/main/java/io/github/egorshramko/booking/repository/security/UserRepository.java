package io.github.egorshramko.booking.repository.security;

import io.github.egorshramko.booking.model.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByIdAndActualIsTrue(Long userId);
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndActualIsTrue(String username);

}
