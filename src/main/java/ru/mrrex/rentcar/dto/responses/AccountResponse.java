package ru.mrrex.rentcar.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse {

    @Schema(description = "The username of user", example = "John Doe")
    @JsonProperty("username")
    private String username;

    @Schema(description = "The email of the user", example = "john.doe@example.com")
    @JsonProperty("email")
    private String email;
}
