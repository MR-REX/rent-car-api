package ru.mrrex.rentcar.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErrorResponse {

    @JsonProperty("status_code")
    private int statusCode;

    @JsonProperty("message")
    private String message;

    @JsonProperty("timestamp")
    private Long timestamp;

    public ErrorResponse() {
        this.timestamp = System.currentTimeMillis();
    }
}
