package ru.mrrex.rentcar.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @Schema(description = "The username of user", example = "John Doe")
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 5, max = 50, message = "User name must contain from 5 to 50 characters")
    private String username;

    @Schema(description = "The email of the user", example = "john.doe@example.com")
    @NotBlank(message = "Email cannot be empty")
    @Size(min = 5, max = 255, message = "Email address must contain from 5 to 255 characters")
    @Email(message = "Email address must be in the format user@example.com")
    private String email;

    @Schema(description = "The password of the user", example = "password")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 255, message = "Password must contain from 8 to 255 characters")
    private String password;
}
