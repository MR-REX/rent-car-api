package ru.mrrex.rentcar.dto.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.mrrex.rentcar.dto.responses.DetailedCarResponseDto;
import ru.mrrex.rentcar.models.Car;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {BrandMapper.class, CategoryMapper.class})
public interface DetailedCarMapper {

    @Mapping(source = "publicId", target = "id")
    @Mapping(source = "rentalPricePerDay", target = "rentalPricePerDay")
    DetailedCarResponseDto toDetailedCarResponseDto(Car car);

    List<DetailedCarResponseDto> toDetailedCarResponseDtoList(List<Car> cars);
}
