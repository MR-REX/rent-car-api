package ru.mrrex.rentcar.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mrrex.rentcar.dto.requests.RegisterRequest;
import ru.mrrex.rentcar.enums.Role;
import ru.mrrex.rentcar.exceptions.NonUniqueEmailException;
import ru.mrrex.rentcar.exceptions.UsernameAlreadyTakenException;
import ru.mrrex.rentcar.models.User;
import ru.mrrex.rentcar.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private static final String USERNAME = "JohnDoe";
    private static final String PASSWORD = "Password";
    private static final String EMAIL = "john.doe@example.com";

    private RegisterRequest generateRegisterRequest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(USERNAME);
        registerRequest.setPassword(PASSWORD);
        registerRequest.setEmail(EMAIL);

        return registerRequest;
    }

    @Test
    public void testCreateUser_Success()
            throws UsernameAlreadyTakenException, NonUniqueEmailException {
        RegisterRequest registerRequest = generateRegisterRequest();

        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setRole(Role.ROLE_USER);

        when(userRepository.existsByUsername(USERNAME)).thenReturn(false);
        when(userRepository.existsByEmail(EMAIL)).thenReturn(false);
        when(passwordEncoder.encode(PASSWORD)).thenReturn("EncodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(registerRequest);

        assertNotNull(createdUser);
        assertEquals(USERNAME, createdUser.getUsername());
        assertEquals(PASSWORD, createdUser.getPassword());
        assertEquals(EMAIL, createdUser.getEmail());
        assertEquals(Role.ROLE_USER, createdUser.getRole());

        verify(userRepository).existsByUsername(USERNAME);
        verify(userRepository).existsByEmail(EMAIL);
        verify(passwordEncoder).encode(PASSWORD);
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testCreateUser_UsernameAlreadyTaken() {
        RegisterRequest registerRequest = generateRegisterRequest();
        when(userRepository.existsByUsername(USERNAME)).thenReturn(true);

        assertThrows(UsernameAlreadyTakenException.class,
                () -> userService.createUser(registerRequest));

        verify(userRepository).existsByUsername(USERNAME);
        verify(userRepository, never()).existsByEmail(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testCreateUser_NonUniqueEmail() {
        RegisterRequest registerRequest = generateRegisterRequest();

        when(userRepository.existsByUsername(USERNAME)).thenReturn(false);
        when(userRepository.existsByEmail(EMAIL)).thenReturn(true);

        assertThrows(NonUniqueEmailException.class, () -> userService.createUser(registerRequest));

        verify(userRepository).existsByUsername(USERNAME);
        verify(userRepository).existsByEmail(EMAIL);
        verify(userRepository, never()).save(any(User.class));
    }
}
