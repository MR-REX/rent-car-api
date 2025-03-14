package ru.mrrex.rentcar.dto.responses;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityValidationErrorResponse extends ErrorResponseDto {

    @JsonProperty("field_errors")
    private Map<String, String> fieldErrors;

    @JsonProperty("global_errors")
    private Map<String, String> globalErrors;
}
