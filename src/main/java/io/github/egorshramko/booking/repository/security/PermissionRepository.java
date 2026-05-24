package io.github.egorshramko.booking.repository.security;

import io.github.egorshramko.booking.model.security.ObjectType;
import io.github.egorshramko.booking.model.security.Permission;
import io.github.egorshramko.booking.model.security.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    Optional<Permission> getByTypeAndObject(PermissionType type, ObjectType object);

}
