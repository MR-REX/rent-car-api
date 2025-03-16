package ru.mrrex.rentcar.dto.responses;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DetailedCarResponse extends CarResponse {

    @JsonProperty("brand")
    private BrandResponse brand;

    @JsonProperty("categories")
    private List<CategoryResponse> categories;

    @JsonProperty("available_for_rent")
    private Integer availableForRent;

    @JsonProperty("colors")
    private List<String> colors;
}
