package io.github.egorshramko.booking.repository.security;

import io.github.egorshramko.booking.model.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
