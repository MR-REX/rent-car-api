package ru.mrrex.rentcar.dto.responses;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DetailedCarResponseDto extends CarResponseDto {

    @JsonProperty("brand")
    private BrandResponseDto brand;

    @JsonProperty("categories")
    private List<CategoryResponseDto> categories;

    @JsonProperty("available_for_rent")
    private Integer availableForRent;

    @JsonProperty("colors")
    private List<String> colors;
}
