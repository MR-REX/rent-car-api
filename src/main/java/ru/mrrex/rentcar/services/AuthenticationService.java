package ru.mrrex.rentcar.services;

import ru.mrrex.rentcar.dto.requests.AuthenticationRequest;
import ru.mrrex.rentcar.dto.requests.RegisterRequest;
import ru.mrrex.rentcar.dto.responses.JwtAuthenticationResponse;
import ru.mrrex.rentcar.exceptions.NonUniqueEmailException;
import ru.mrrex.rentcar.exceptions.UsernameAlreadyTakenException;

public interface AuthenticationService {

    JwtAuthenticationResponse signUp(RegisterRequest registerRequest)
            throws UsernameAlreadyTakenException, NonUniqueEmailException;

    JwtAuthenticationResponse signIn(AuthenticationRequest authenticationRequest);
}
