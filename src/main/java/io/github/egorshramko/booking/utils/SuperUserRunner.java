package io.github.egorshramko.booking.utils;

import io.github.egorshramko.booking.model.security.Role;
import io.github.egorshramko.booking.model.security.User;
import io.github.egorshramko.booking.repository.security.RoleRepository;
import io.github.egorshramko.booking.repository.security.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


/**
 * Класс, создающий суперпользователя в dev и test средах
 */
@Slf4j
@Component
@Profile({"dev", "test"})
public class SuperUserRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public SuperUserRunner(UserRepository userRepository,
                           RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @NullMarked
    @Transactional
    public void run(String... args) throws Exception {

        log.info("Searching super user role in system");
        //Поиск роли суперпользователя
        Role superuserRole = roleRepository.findByName("SUPERUSER")
                .orElseThrow(() -> new RoleNotFoundException("Role SUPERUSER not found"));

        Optional<User> superuserOptional = userRepository.findByUsername("root");
        if (superuserOptional.isEmpty()) {

            log.info("Creating superuser");
            User superuser = User.builder()
                    .actual(true)
                    .username("root")
                    .password(passwordEncoder.encode("root"))
                    .build();
            superuser.addRole(superuserRole);
            userRepository.save(superuser);
        }
        else {
            User superuser = superuserOptional.get();
            if (!superuser.isActual()) {
                superuser.setActual(true);
            }
        }

    }
}
