package ru.mrrex.rentcar.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Username cannot be empty")
    @Size(min = 5, max = 50, message = "User name must contain from 5 to 50 characters")
    private String username;

    @NotBlank(message = "Email cannot be empty")
    @Size(min = 5, max = 255, message = "Email address must contain from 5 to 255 characters")
    @Email(message = "Email address must be in the format user@example.com")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 255, message = "Password must contain from 8 to 255 characters")
    private String password;
}
