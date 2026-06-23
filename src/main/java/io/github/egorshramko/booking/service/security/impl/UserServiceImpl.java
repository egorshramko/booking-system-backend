package io.github.egorshramko.booking.service.security.impl;

import io.github.egorshramko.booking.exception.EmptyRequiredFieldException;
import io.github.egorshramko.booking.exception.UserUniqueException;
import io.github.egorshramko.booking.model.security.Role;
import io.github.egorshramko.booking.model.security.User;
import io.github.egorshramko.booking.repository.security.RoleRepository;
import io.github.egorshramko.booking.repository.security.UserRepository;
import io.github.egorshramko.booking.service.security.UserEntityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleNotFoundException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserEntityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsernameAndActualIsTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

    @Override
    @Transactional
    public User addUser(User user) throws EmptyRequiredFieldException {

        log.debug("Start addUser method");

        //проверка, что пользователя с таким именем не существует
        final String username = user.getUsername();
        final Optional<User> userOptional = userRepository.findByUsernameAndActualIsTrue(username);
        if (userOptional.isPresent()) {
            log.warn("User with username {} is already exists", username);
            throw new UserUniqueException("User with username " + username + " is already exists");
        }

        //шифрование пароля и установка актуальности для пользователя
        final String encodedPassword = passwordEncoder.encode(user.getPassword());

        if (encodedPassword != null) {
            user.setPassword(encodedPassword);
        }
        else {
            log.warn("Empty encoded password in user entity");
            throw new EmptyRequiredFieldException("Empty encoded password in user entity");
        }
        user.setActual(true);

        //TODO: добавить дефолтную роль для пользователя при создании

        //сохранение пользователя
        log.info("Saving user");
        log.debug("Finish addUser method");
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findByIdAndActualIsTrue(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found"));
    }

    @Override
    @Transactional
    public User editUser(User user) {

        log.debug("Start editUser method");

        log.debug("user: {}", user);

        Set<String> roleNames = user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet());

        user.getRoles().clear();
        for (String roleName : roleNames) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new EntityNotFoundException("Role with name " + roleName + " not found"));
            user.addRole(role);
        }

        log.debug("user: {}", user);

        return userRepository.save(user);

    }

    @Override
    @Transactional
    public void removeUser(Long userId) {
        User removingUser = userRepository.findByIdAndActualIsTrue(userId)
                .orElse(null);
        if (removingUser != null) {
            removingUser.setActual(false);
            userRepository.save(removingUser);
        }
    }

    @Override
    public Page<User> getUsersPage(Integer pageNumber) {
        return userRepository.findAllByActualIsTrue(PageRequest.of(pageNumber, 20,
                Sort.by("username").ascending()));
    }

    @Override
    public Integer getUsersPagesCount() {
        Page<User> usersPage = this.getUsersPage(0);
        return usersPage.getTotalPages();
    }
}
