package ru.mrrex.rentcar.services.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.mrrex.rentcar.dto.requests.AuthenticationRequest;
import ru.mrrex.rentcar.dto.requests.RegisterRequest;
import ru.mrrex.rentcar.dto.responses.JwtAuthenticationResponse;
import ru.mrrex.rentcar.exceptions.NonUniqueEmailException;
import ru.mrrex.rentcar.exceptions.UsernameAlreadyTakenException;
import ru.mrrex.rentcar.models.User;
import ru.mrrex.rentcar.services.AuthenticationService;
import ru.mrrex.rentcar.services.JwtService;
import ru.mrrex.rentcar.services.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public JwtAuthenticationResponse signUp(@Valid RegisterRequest registerRequest)
            throws UsernameAlreadyTakenException, NonUniqueEmailException {
        User user = userService.createUser(registerRequest);
        String jwtToken = jwtService.generateToken(user);

        return new JwtAuthenticationResponse(jwtToken);
    }

    @Override
    public JwtAuthenticationResponse signIn(AuthenticationRequest authenticationRequest) {
        String username = authenticationRequest.getUsername();

        Authentication authentication = new UsernamePasswordAuthenticationToken(username,
                authenticationRequest.getPassword());

        authenticationManager.authenticate(authentication);

        User user = userService.getUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                
        String jwtToken = jwtService.generateToken(user);

        return new JwtAuthenticationResponse(jwtToken);
    }
}
