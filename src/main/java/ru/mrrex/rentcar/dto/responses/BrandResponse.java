package ru.mrrex.rentcar.dto.responses;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BrandResponse {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("image_url")
    private String imageUrl;
}
