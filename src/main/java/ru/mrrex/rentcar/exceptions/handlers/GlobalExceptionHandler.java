package ru.mrrex.rentcar.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import lombok.extern.slf4j.Slf4j;
import ru.mrrex.rentcar.dto.responses.ErrorResponseDto;
import ru.mrrex.rentcar.exceptions.ApplicationError;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ApplicationError.class)
    public ResponseEntity<ErrorResponseDto> catchApplicationError(ApplicationError error) {
        log.error(error.getMessage(), error);

        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setStatusCode(error.getStatusCode().value());
        errorResponse.setMessage(error.getMessage());
        errorResponse.setTimestamp(error.getCreatedAt());

        return new ResponseEntity<>(errorResponse, error.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> catchArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception) {
        log.error(exception.getMessage(), exception);

        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
