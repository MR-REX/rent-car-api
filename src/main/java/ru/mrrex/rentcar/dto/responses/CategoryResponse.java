package ru.mrrex.rentcar.dto.responses;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

    @Schema(description = "The unique public identifier of the category",
            example = "5c281c75-cc89-46cb-a9fb-c32aae68df60")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "The name of the category", example = "City Car")
    @JsonProperty("name")
    private String name;
}
