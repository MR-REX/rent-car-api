package ru.mrrex.rentcar.exceptions;

import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.BindingResult;
import lombok.Getter;

@Getter
public class EntityValidationError extends RuntimeException {

    private final Map<String, String> fieldErrors;
    private final Map<String, String> globalErrors;

    public EntityValidationError(BindingResult bindingResult) {
        super("Error during entity validation");

        fieldErrors = new HashMap<>();

        bindingResult.getFieldErrors().forEach(fieldError -> {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        globalErrors = new HashMap<>();

        bindingResult.getGlobalErrors().forEach(globalError -> {
            globalErrors.put(globalError.getObjectName(), globalError.getDefaultMessage());
        });
    }
}
