package io.github.egorshramko.booking.service.security.impl;

import io.github.egorshramko.booking.exception.EmptyRequiredFieldException;
import io.github.egorshramko.booking.model.security.User;
import io.github.egorshramko.booking.repository.security.UserRepository;
import io.github.egorshramko.booking.service.security.UserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService, UserEntityService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));
    }

    @Override
    public User addUser(User user) throws EmptyRequiredFieldException {



        return null;
    }

    @Override
    public User getUserById(Long userId) {
        return null;
    }

    @Override
    public User editUser(User user) {
        return null;
    }

    @Override
    public void removeUser(Long userId) {

    }

    @Override
    public Page<User> getUsersPage(Integer pageNumber) {
        return null;
    }
}
