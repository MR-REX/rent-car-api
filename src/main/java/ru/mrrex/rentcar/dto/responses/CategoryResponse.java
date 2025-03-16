package ru.mrrex.rentcar.dto.responses;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    private String name;
}
