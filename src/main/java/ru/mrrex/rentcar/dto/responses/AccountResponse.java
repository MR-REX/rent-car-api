package ru.mrrex.rentcar.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountResponse {

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;
}
