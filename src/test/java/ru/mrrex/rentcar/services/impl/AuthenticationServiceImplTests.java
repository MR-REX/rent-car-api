package ru.mrrex.rentcar.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.mrrex.rentcar.dto.requests.AuthenticationRequest;
import ru.mrrex.rentcar.dto.requests.RegisterRequest;
import ru.mrrex.rentcar.dto.responses.JwtAuthenticationResponse;
import ru.mrrex.rentcar.enums.Role;
import ru.mrrex.rentcar.exceptions.NonUniqueEmailException;
import ru.mrrex.rentcar.exceptions.UsernameAlreadyTakenException;
import ru.mrrex.rentcar.models.User;
import ru.mrrex.rentcar.services.JwtService;
import ru.mrrex.rentcar.services.UserService;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTests {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private RegisterRequest createRegisterRequest(String username, String email, String password) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(username);
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);

        return registerRequest;
    }

    @Test
    public void testSignUp_Success() throws UsernameAlreadyTakenException, NonUniqueEmailException {
        RegisterRequest registerRequest =
                createRegisterRequest("JohnDoe", "john.doe@example.com", "password");

        User user = new User(1L, "JohnDoe", "john.doe@example.com", "password", Role.ROLE_USER);
        String jwtToken = "jwt-token";

        when(userService.createUser(registerRequest)).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn(jwtToken);

        JwtAuthenticationResponse response = authenticationService.signUp(registerRequest);

        assertNotNull(response);
        assertEquals(jwtToken, response.getToken());

        verify(userService).createUser(registerRequest);
        verify(jwtService).generateToken(user);
    }

    @Test
    public void testSignUp_UsernameAlreadyTaken()
            throws UsernameAlreadyTakenException, NonUniqueEmailException {
        RegisterRequest registerRequest =
                createRegisterRequest("JohnDoe", "john.doe@example.com", "password");

        when(userService.createUser(registerRequest))
                .thenThrow(new UsernameAlreadyTakenException());

        assertThrows(UsernameAlreadyTakenException.class,
                () -> authenticationService.signUp(registerRequest));
    }

    @Test
    public void testSignUp_NonUniqueEmail()
            throws UsernameAlreadyTakenException, NonUniqueEmailException {
        RegisterRequest registerRequest =
                createRegisterRequest("JohnDoe", "john.doe@example.com", "password");

        when(userService.createUser(registerRequest)).thenThrow(new NonUniqueEmailException());

        assertThrows(NonUniqueEmailException.class,
                () -> authenticationService.signUp(registerRequest));
    }

    @Test
    public void testSignIn_Success() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername("JohnDoe");
        authenticationRequest.setPassword("password");

        User user = new User(1L, "JohnDoe", "john.doe@example.com", "password", Role.ROLE_USER);
        String jwtToken = "jwt-token";

        when(userService.getUserByUsername("JohnDoe")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn(jwtToken);

        JwtAuthenticationResponse response = authenticationService.signIn(authenticationRequest);

        assertNotNull(response);
        assertEquals(jwtToken, response.getToken());

        verify(authenticationManager).authenticate(any(Authentication.class));
        verify(userService).getUserByUsername("JohnDoe");
        verify(jwtService).generateToken(user);
    }

    @Test
    public void testSignIn_UserNotFound() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest();
        authenticationRequest.setUsername("JohnDoe");
        authenticationRequest.setPassword("password");

        when(userService.getUserByUsername("JohnDoe")).thenReturn(Optional.empty());
        
        assertThrows(UsernameNotFoundException.class,
                () -> authenticationService.signIn(authenticationRequest));
    }
}
