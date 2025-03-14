package ru.mrrex.rentcar.services.impl;

import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.mrrex.rentcar.dto.requests.RegisterRequest;
import ru.mrrex.rentcar.enums.Role;
import ru.mrrex.rentcar.exceptions.NonUniqueEmailException;
import ru.mrrex.rentcar.exceptions.UsernameAlreadyTakenException;
import ru.mrrex.rentcar.models.User;
import ru.mrrex.rentcar.repositories.UserRepository;
import ru.mrrex.rentcar.services.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public User createUser(@Valid RegisterRequest registerRequest)
            throws UsernameAlreadyTakenException, NonUniqueEmailException {
        String username = registerRequest.getUsername();

        if (userRepository.existsByUsername(username))
            throw new UsernameAlreadyTakenException(username);

        String email = registerRequest.getEmail();

        if (userRepository.existsByEmail(email))
            throw new NonUniqueEmailException(email);

        User user = User.builder()
            .username(username)
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .email(registerRequest.getEmail())
            .role(Role.ROLE_USER)
            .build();

        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByUsername(username);
    }
}
