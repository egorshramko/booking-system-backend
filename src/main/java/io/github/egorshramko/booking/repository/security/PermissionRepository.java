package io.github.egorshramko.booking.repository.security;

import io.github.egorshramko.booking.model.security.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
