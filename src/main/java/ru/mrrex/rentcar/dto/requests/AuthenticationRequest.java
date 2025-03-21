package ru.mrrex.rentcar.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequest {

    @Schema(description = "The username of user", example = "John Doe")
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 5, max = 50, message = "Username must contain from 5 to 50 characters")
    private String username;

    @Schema(description = "The password of the user", example = "password")
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 8, max = 255, message = "Password must contain from 8 to 255 characters")
    private String password;
}
