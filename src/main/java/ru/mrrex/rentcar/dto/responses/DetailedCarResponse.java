package ru.mrrex.rentcar.dto.responses;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.mrrex.rentcar.enums.CarColor;

@Data
@EqualsAndHashCode(callSuper = true)
public class DetailedCarResponse extends CarResponse {

    @Schema(description = "Car brand")
    @JsonProperty("brand")
    private BrandResponse brand;

    @Schema(description = "List of car categories")
    @JsonProperty("categories")
    private List<CategoryResponse> categories;

    @Schema(description = "Number of available cars for rent", example = "1")
    @JsonProperty("available_for_rent")
    private Integer availableForRent;

    @Schema(description = "List of car colors")
    @JsonProperty("colors")
    private List<CarColor> colors;
}
