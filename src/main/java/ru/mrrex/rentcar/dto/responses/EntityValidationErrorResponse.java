package ru.mrrex.rentcar.dto.responses;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityValidationErrorResponse extends ErrorResponse {

    @Schema(description = "Field errors map")
    @JsonProperty("field_errors")
    private Map<String, String> fieldErrors;

    @Schema(description = "Global errors map")
    @JsonProperty("global_errors")
    private Map<String, String> globalErrors;
}
