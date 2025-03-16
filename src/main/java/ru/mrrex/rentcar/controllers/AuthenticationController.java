package ru.mrrex.rentcar.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication Controller",
        description = "Operations related to user registration and authorization in application")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user", description = "Registers a new user based on the provided data")
    @ApiResponse(responseCode = "201", description = "User registered")
    @ApiResponse(responseCode = "400", description = "Incorrect user data")
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
    @Operation(summary = "Authorize the user", description = "Authorizes user and returns auth token")
    @ApiResponse(responseCode = "200", description = "Authorization is successful")
    @ApiResponse(responseCode = "422", description = "User authentication failed")
    public JwtAuthenticationResponse signIn(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        try {
            return authenticationService.signIn(authenticationRequest);
        } catch (Exception exception) {
            throw new ApplicationError(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage());
        }
    }
}
