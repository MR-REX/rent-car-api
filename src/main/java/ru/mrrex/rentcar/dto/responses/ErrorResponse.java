package ru.mrrex.rentcar.dto.responses;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ErrorResponse {

    @Schema(description = "The returned status code", example = "500")
    @JsonProperty("status_code")
    private int statusCode;

    @Schema(description = "Error text", example = "Internal server error")
    @JsonProperty("message")
    private String message;

    @Schema(description = "Error timestamp")
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public void setTimestamp(long milliseconds) {
        this.timestamp =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneId.systemDefault());
    }
}
