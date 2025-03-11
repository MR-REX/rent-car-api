package ru.mrrex.rentcar.exceptions;

import org.springframework.http.HttpStatus;
import lombok.Getter;

@Getter
public class ApplicationError extends RuntimeException {

    private final HttpStatus statusCode;
    private final long createdAt;

    public ApplicationError(HttpStatus statusCode, String message, Throwable throwable) {
        super(message, throwable);

        this.statusCode = statusCode;
        this.createdAt = System.currentTimeMillis();
    }

    public ApplicationError(HttpStatus statusCode, String message) {
        super(message);

        this.statusCode = statusCode;
        this.createdAt = System.currentTimeMillis();
    }

    public ApplicationError(HttpStatus statusCode) {
        this(statusCode, "An error has occurred");
    }

    public ApplicationError() {
        this(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
