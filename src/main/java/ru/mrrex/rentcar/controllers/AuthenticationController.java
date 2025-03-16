package ru.mrrex.rentcar.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.mrrex.rentcar.dto.requests.AuthenticationRequest;
import ru.mrrex.rentcar.dto.requests.RegisterRequest;
import ru.mrrex.rentcar.dto.responses.JwtAuthenticationResponse;
import ru.mrrex.rentcar.exceptions.ApplicationError;
import ru.mrrex.rentcar.exceptions.EntityValidationError;
import ru.mrrex.rentcar.services.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public JwtAuthenticationResponse signUp(@Valid @RequestBody RegisterRequest registerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new EntityValidationError(bindingResult);

        try {
            return authenticationService.signUp(registerRequest);
        } catch (Exception exception) {
            throw new ApplicationError(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @PostMapping("/signin")
    public JwtAuthenticationResponse signIn(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        try {
            return authenticationService.signIn(authenticationRequest);
        } catch (Exception exception) {
            throw new ApplicationError(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
        }
    }
}
