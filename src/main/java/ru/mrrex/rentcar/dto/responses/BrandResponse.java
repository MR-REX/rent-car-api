package ru.mrrex.rentcar.dto.responses;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BrandResponse {

    @Schema(description = "The unique public identifier of the brand",
            example = "5c281c75-cc89-46cb-a9fb-c32aae68df60")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "The name of the brand", example = "Nissan")
    @JsonProperty("name")
    private String name;

    @Schema(description = "The image URL of the brand logo")
    @JsonProperty("image_url")
    private String imageUrl;
}
