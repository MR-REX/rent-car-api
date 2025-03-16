package ru.mrrex.rentcar.dto.responses;

import java.math.BigDecimal;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CarResponse {

    @Schema(description = "The unique public identifier of the car",
            example = "5c281c75-cc89-46cb-a9fb-c32aae68df60")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "The name of the car", example = "Nissan GT-R")
    @JsonProperty("name")
    private String name;

    @Schema(description = "The description of the car", example = "A really fast car.")
    @JsonProperty("description")
    private String description;

    @Schema(description = "The image URL of the car")
    @JsonProperty("image_url")
    private String imageUrl;

    @Schema(description = "A car rental price per day", example = "90")
    @JsonProperty("rental_price_per_day")
    private BigDecimal rentalPricePerDay;
}
