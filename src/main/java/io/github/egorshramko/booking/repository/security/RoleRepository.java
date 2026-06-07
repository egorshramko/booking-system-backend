package io.github.egorshramko.booking.repository.security;

import io.github.egorshramko.booking.model.security.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
    Optional<Role> findByIdAndActualIsTrue(Long id);
    Page<Role> findAllByActualIsTrue(Pageable pageable);

}
